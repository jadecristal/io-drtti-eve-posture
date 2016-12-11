package io.drtti.eve.ejb.sso;

import io.drtti.eve.dom.sso.*;

import org.apache.log4j.Logger;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthAuthzResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * Implements the CCP EVE SSO OAuth2 flow. Constants are stored in CcpEveSsoOAuth2Config.
 * This approximately looks like:
 * <p>
 * OAuth2 AUTHORIZATION FLOW:
 * User-Agent: Click triggers HTTPS GET to SSO-Endpoint-Auth using URI from getAuthRequestURI()
 * User-Agent: User authenticates and approves request at SSO-Endpoint-Auth
 * SSO-Endpoint-Auth: Redirects User-Agent to HTTPS GET to DRTTI callback URI with auth code
 * <p>
 * OAuth2 TOKEN FLOW
 * Bean: Makes HTTPS POST for user to SSO-Endpoint-Token asking for tokens with auth code
 * SSO-Endpoint-Token: Returns access and refresh tokens, with expiration time (seconds)
 * Bean: Can now make API request to approved scopes as authenticated characterId
 * Bean: If access_token expired, send the refresh_token to SSO-Endpoint-Token for a new one
 * @author cwinebrenner
 */
@Stateful(passivationCapable = false)
public class CcpEveSsoOAuth2SessionBean {

    private final Logger log = Logger.getLogger(this.getClass());

    private HttpSession session;
    private CcpEveSsoOAuth2Credential credential;

    public CcpEveSsoOAuth2SessionBean() {
        this.credential = new CcpEveSsoOAuth2Credential();
    }

    /**
     * Associates a HttpSession for the current user session with this bean instance.
     * @param session HttpSession for the current user
     */
    public void bindSession(HttpSession session) {
        if (this.session == null) {
            this.session = session;
        }
    }

    /**
     * Provides the correct OAuth2 URI for the current user session.
     * @return String with the URI to be used be used for the CCP EVE SSO for this user session, or null
     */
    public String getAuthRequestURI() {
        try {
            // TODO: as part of hardening state, do some JSESSIONID + something hashing and store it for use below
            OAuthClientRequest authRequest = OAuthClientRequest
                    .authorizationLocation(CcpEveSsoOAuth2Config.CCP_EVE_SSO_OAUTH2_ENDPOINT_AUTH_URI)
                    .setParameter("response_type", CcpEveSsoOAuth2Config.CCP_EVE_SSO_OAUTH2_ENDPOINT_AUTH_RESPONSE_TYPE)
                    .setRedirectURI(CcpEveSsoOAuth2Config.DRTTI_CCP_EVE_SSO_OAUTH2_CALLBACK_URI)
                    .setClientId(CcpEveSsoOAuth2Config.DRTTI_CCP_EVE_SSO_OAUTH2_CLIENT_ID)
                    .setState(session.getId())  // TODO: Harden state value more
                    .buildQueryMessage();
            return authRequest.getLocationUri();

        } catch (OAuthSystemException oase) {
            log.error(oase);
            return null;
        }
    }

    /**
     * Checks the purported OAuth2 callback for the expected parameters.
     * @param req HttpServletRequest from the OAuth2 callback URI request
     * @return Whether or not the OAuth2 'code' and 'state' were present in the HttpServletRequest
     */
    public boolean containsAuthCallback(HttpServletRequest req) {
        return ((req.getParameter("code") != null) && (req.getParameter("state") != null));
    }

    /**
     * Handles the returned OAuth2 authentication request to get access tokens.
     * @param req HttpServletRequest from the OAuth2 callback URI request
     */
    public void processAuthCallback(HttpServletRequest req) {
        try {
            OAuthAuthzResponse authResponse = OAuthAuthzResponse.oauthCodeAuthzResponse(req);
            requestAccessToken(authResponse);
        } catch (OAuthProblemException oape) {
            log.error(oape);
        }
    }

    /**
     * Internal method to get the initial OAuth2 credentials for this session.
     * @param authResponse OAuthAuthzReponse containing OAuth2 callback parameters
     */
    private void requestAccessToken(OAuthAuthzResponse authResponse) {
        try {
            OAuthClientRequest tokenRequest = OAuthClientRequest
                    .tokenLocation(CcpEveSsoOAuth2Config.CCP_EVE_SSO_OAUTH2_ENDPOINT_TOKEN_URI)
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId(CcpEveSsoOAuth2Config.DRTTI_CCP_EVE_SSO_OAUTH2_CLIENT_ID)
                    .setClientSecret(CcpEveSsoOAuth2Config.DRTTI_CCP_EVE_SSO_OAUTH2_CLIENT_SECRET)
                    .setCode(authResponse.getCode())
                    .buildQueryMessage();

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthAccessTokenResponse tokenResponse = oAuthClient.accessToken(tokenRequest);
            storeCredential(tokenResponse);

        } catch (OAuthSystemException oase) {
            log.error(CcpEveSsoOAuth2Config.EXCEPTION_OAUTH2_SYSTEM, oase);
        } catch (OAuthProblemException oape) {
            log.error(CcpEveSsoOAuth2Config.EXCEPTION_OAUTH2_PROBLEM, oape);
        }
    }

    private void refreshAccessToken() {
        try {
            OAuthClientRequest refreshRequest = OAuthClientRequest
                    .tokenLocation(CcpEveSsoOAuth2Config.CCP_EVE_SSO_OAUTH2_ENDPOINT_TOKEN_URI)
                    .setGrantType(GrantType.REFRESH_TOKEN)
                    .setClientId(CcpEveSsoOAuth2Config.DRTTI_CCP_EVE_SSO_OAUTH2_CLIENT_ID)
                    .setClientSecret(CcpEveSsoOAuth2Config.DRTTI_CCP_EVE_SSO_OAUTH2_CLIENT_SECRET)
                    .setRefreshToken(credential.getRefreshToken())
                    .buildQueryMessage();

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthAccessTokenResponse refreshResponse = oAuthClient.accessToken(refreshRequest);
            storeCredential(refreshResponse);

        } catch (OAuthSystemException oase) {
            log.error(CcpEveSsoOAuth2Config.EXCEPTION_OAUTH2_SYSTEM, oase);
        } catch (OAuthProblemException oape) {
            log.error(CcpEveSsoOAuth2Config.EXCEPTION_OAUTH2_PROBLEM, oape);
        }
    }

    public String crestGetAuthenticatedPilot() {
        try {

            if (credential.isExpired())  {
                refreshAccessToken();
            }
            OAuthClientRequest crestBearer = new OAuthBearerClientRequest(CcpEveSsoOAuth2Config.CREST_VERIFY_ENDPOINT)
                    .setAccessToken(credential.getAccessToken()).buildQueryMessage();

            OAuthClient crestClient = new OAuthClient(new URLConnectionClient());
            OAuthResourceResponse crestResponse = crestClient.resource(crestBearer, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
            return crestResponse.getBody();

        } catch (OAuthSystemException oase) {
            log.error(CcpEveSsoOAuth2Config.EXCEPTION_OAUTH2_SYSTEM, oase);
            return null;
        } catch (OAuthProblemException oape) {
            log.error(CcpEveSsoOAuth2Config.EXCEPTION_OAUTH2_PROBLEM, oape);
            return null;
        }
    }

    private void storeCredential(OAuthAccessTokenResponse tokenResponse) {
        credential.setOrReset(tokenResponse.getAccessToken(), tokenResponse.getTokenType(), tokenResponse.getExpiresIn(), tokenResponse.getRefreshToken());
    }

    public boolean isAuthenticated() {
        return credential.isAuthenticated();
    }

    @Remove
    public void remove() {
        this.credential = null;
        this.session = null;
    }

}

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

import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Base64;

/**
 * Helps manipulate credentials for the CCP EVE SSO OAuth2 flow.
 * Constants specific to this application are stored in CcpEveSsoOAuth2Config.
 * The CCP EVE SSO OAuth2 flows are:
 * <p>
 * OAuth2 AUTHORIZATION FLOW:
 * User-Agent: Click triggers HTTPS GET to SSO-Endpoint-Auth using URI from getAuthRequestURI()
 * User-Agent: User authenticates and approves request at SSO-Endpoint-Auth
 * SSO-Endpoint-Auth: Redirects User-Agent to HTTPS GET to DRTTI callback URI with auth code
 * <p>
 * OAuth2 TOKEN FLOW
 * Bean: Makes HTTPS POST for user to SSO-Endpoint-Token asking for tokens with auth code
 * SSO-Endpoint-Token: Returns access and refresh tokens, with expiration time (seconds)
 * Bean: If access_token expired, send the refresh_token to SSO-Endpoint-Token for a new access_token
 *
 * @author cwinebrenner
 */
@Stateless
public class CcpEveSsoOAuth2Bean {

    private final Logger log = Logger.getLogger(this.getClass());

    public CcpEveSsoOAuth2Bean() {
    }

    public String getAuthRequestURI(HttpSession session) {

        try {
            // TODO: as part of hardening state, do some JSESSIONID + something hashing and store it for use below
            OAuthClientRequest authRequest = OAuthClientRequest
                    .authorizationLocation(CcpEveSsoOAuth2Config.CCP_EVE_SSO_OAUTH2_ENDPOINT_AUTH_URI)
                    .setParameter("response_type", CcpEveSsoOAuth2Config.CCP_EVE_SSO_OAUTH2_ENDPOINT_AUTH_RESPONSE_TYPE)
                    .setRedirectURI(CcpEveSsoOAuth2Config.DRTTI_CCP_EVE_SSO_OAUTH2_CALLBACK_URI)
                    .setClientId(CcpEveSsoOAuth2Config.DRTTI_CCP_EVE_SSO_OAUTH2_CLIENT_ID)
                    .setState(session.getId())  // TODO: Harden state value more
                    .setParameter("scopes", CcpEveSsoOAuth2Config.CCP_EVE_SSO_OAUTH2_SCOPES)
                    .buildQueryMessage();
            return authRequest.getLocationUri();

        } catch (OAuthSystemException oase) {
            log.error(oase);
            return null;
        }
    }

    public boolean containsAuthCallback(HttpServletRequest request) {
        return ((request.getParameter("code") != null) && (request.getParameter("state") != null));
    }

    public CcpEveSsoOAuth2Credential processAuthCallback(HttpServletRequest req) {

        try {
            OAuthAuthzResponse authResponse = OAuthAuthzResponse.oauthCodeAuthzResponse(req);
            return requestAccessToken(authResponse);
        } catch (OAuthProblemException oape) {
            log.error(oape);
            return null;
        }
    }

    private CcpEveSsoOAuth2Credential requestAccessToken(OAuthAuthzResponse authResponse) {
        try {

            OAuthClientRequest accessTokenRequest = OAuthClientRequest
                    .tokenLocation(CcpEveSsoOAuth2Config.CCP_EVE_SSO_OAUTH2_ENDPOINT_TOKEN_URI)
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setCode(authResponse.getCode())
                    .buildQueryMessage();
            accessTokenRequest.setHeader("Authorization", "Basic " + buildDrttiHttpAuthenticationHeader());

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthAccessTokenResponse accessTokenResponse = oAuthClient.accessToken(accessTokenRequest, OAuth.HttpMethod.POST);

            return buildCredentialFromTokenResponse(accessTokenResponse);

        } catch (OAuthSystemException oase) {
            log.error(CcpEveSsoOAuth2Config.EXCEPTION_OAUTH2_SYSTEM, oase);
            return null;
        } catch (OAuthProblemException oape) {
            log.error(CcpEveSsoOAuth2Config.EXCEPTION_OAUTH2_PROBLEM, oape);
            return null;
        }
    }

    public CcpEveSsoOAuth2Credential refreshAccessToken(CcpEveSsoOAuth2Credential credential) {
        try {

            OAuthClientRequest refreshTokenRequest = OAuthClientRequest
                    .tokenLocation(CcpEveSsoOAuth2Config.CCP_EVE_SSO_OAUTH2_ENDPOINT_TOKEN_URI)
                    .setGrantType(GrantType.REFRESH_TOKEN)
                    .setRefreshToken(credential.getRefreshToken())
                    .buildQueryMessage();
            refreshTokenRequest.setHeader("Authorization", buildDrttiHttpAuthenticationHeader());

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthAccessTokenResponse refreshTokenResponse = oAuthClient.accessToken(refreshTokenRequest, OAuth.HttpMethod.POST);

            return buildCredentialFromTokenResponse(refreshTokenResponse);

        } catch (OAuthSystemException oase) {
            log.error(CcpEveSsoOAuth2Config.EXCEPTION_OAUTH2_SYSTEM, oase);
            return null;
        } catch (OAuthProblemException oape) {
            log.error(CcpEveSsoOAuth2Config.EXCEPTION_OAUTH2_PROBLEM, oape);
            return null;
        }
    }

    // TODO: move this to a separate CREST Bean, or fix the endpoint/Config name so that it's the OAuth Verify endpoint
    public String crestGetAuthenticatedPilot(CcpEveSsoOAuth2Credential credential) {
        try {

            OAuthClientRequest crestBearerRequest = new OAuthBearerClientRequest(CcpEveSsoOAuth2Config.CREST_VERIFY_ENDPOINT)
                    .setAccessToken(credential.getAccessToken()).buildQueryMessage();

            OAuthClient crestClient = new OAuthClient(new URLConnectionClient());
            OAuthResourceResponse crestResponse = crestClient.resource(crestBearerRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
            return crestResponse.getBody();

        } catch (OAuthSystemException oase) {
            log.error(CcpEveSsoOAuth2Config.EXCEPTION_OAUTH2_SYSTEM, oase);
            return null;
        } catch (OAuthProblemException oape) {
            log.error(CcpEveSsoOAuth2Config.EXCEPTION_OAUTH2_PROBLEM, oape);
            return null;
        }
    }

    private CcpEveSsoOAuth2Credential buildCredentialFromTokenResponse(OAuthAccessTokenResponse tokenResponse) {
        return new CcpEveSsoOAuth2Credential(tokenResponse.getAccessToken(), tokenResponse.getTokenType(), tokenResponse.getExpiresIn(), tokenResponse.getRefreshToken());
    }

    private String buildDrttiHttpAuthenticationHeader() {
        Base64.Encoder b64 = Base64.getEncoder();
        String ssoClientSecret = CcpEveSsoOAuth2Config.DRTTI_CCP_EVE_SSO_OAUTH2_CLIENT_ID + ":" + CcpEveSsoOAuth2Config.DRTTI_CCP_EVE_SSO_OAUTH2_CLIENT_SECRET;
        return "Basic " + b64.encodeToString(ssoClientSecret.getBytes());
    }

}

package io.drtti.eve.dom.sso;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Stores OAuth2 access and bearer tokens from CCP EVE SSO.
 * Handles logic to check expiration of access_token.
 *
 * @author cwinebrenner
 */
@SessionScoped
public class CcpEveSsoCredential implements Serializable {

    private String accessToken;
    private String tokenType;
    private Long expiresIn;
    private String refreshToken;

    private Instant credentialCreateUpdateInstant;
    private boolean authenticated;

    public CcpEveSsoCredential() {
        this.authenticated = false;
    }

    public CcpEveSsoCredential(String accessToken, String tokenType, Long expiresIn, String refreshToken) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        touchCredentialInstant();
        this.authenticated = true;
    }

    public String getHttpAuthorizationHeader() {
        return (isAuthenticated() && !isExpired()) ? tokenType + " " + accessToken : null;
    }

    private void touchCredentialInstant() {
        this.credentialCreateUpdateInstant = Instant.now();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public boolean isExpired() {
        return (ChronoUnit.SECONDS.between(credentialCreateUpdateInstant, Instant.now()) > expiresIn);
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

}

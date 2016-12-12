package io.drtti.eve.dom.sso;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Stores OAuth2 access and bearer tokens from CCP EVE SSO.
 * Handles logic to check expiration of access_token.
 *
 * @author cwinebrenner
 */
public class CcpEveSsoOAuth2Credential {

    private String accessToken;
    private String tokenType;
    private Long expiresIn;
    private String refreshToken;

    private Instant credentialCreateUpdateInstant;
    private boolean authenticated;

    public CcpEveSsoOAuth2Credential() {
        this.authenticated = false;
    }

    public CcpEveSsoOAuth2Credential(String accessToken, String tokenType, Long expiresIn, String refreshToken) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        touchCredentialInstant();
        this.authenticated = true;
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

package io.drtti.eve.dom.sso;

/**
 * @author cwinebrenner
 */
public final class CcpEveSsoConfig {

    // HttpSession variable
    public static final String DRTTI_USER_KEY = "DrttiUser";
    public static final String DRTTI_EVE_SSO_CREDENTIAL_KEY = "CcpEveSsoCredential";
    public static final String DRTTI_EVE_PILOT_CHARACTER_ID_KEY = "CharacterID";
    public static final String DRTTI_EVE_PILOT_NAME_KEY = "CharacterName";
    public static final String DRTTI_EVE_PILOT_LOCATION_KEY = "CharacterSolarSystemName";
    public static final String DRTTI_EVE_AUTHENTICATED_PILOT_KEY = "AuthenticatedPilot";

    // OAuth2 authorize endpoint configuration constants (application-independent)
    public static final String CCP_EVE_SSO_OAUTH2_ENDPOINT_AUTH_URI = "https://login.eveonline.com/oauth/authorize/";
    public static final String CCP_EVE_SSO_OAUTH2_ENDPOINT_AUTH_RESPONSE_TYPE = "code";

    // OAuth2 token endpoint configuration constants (scopes application-dependent)
    public static final String CCP_EVE_SSO_OAUTH2_ENDPOINT_TOKEN_URI = "https://login.eveonline.com/oauth/token/";
    public static final String CCP_EVE_SSO_OAUTH2_SCOPES_CREST = "characterLocationRead";
    public static final String CCP_EVE_SSO_OAUTH2_SCOPES_ESI = "esi-location.read_location.v1";

    // OAuth2 verify endpoint configuration constants (application-independent)
    public static final String CCP_EVE_SSO_OAUTH2_ENDPOINT_VERIFY = "https://login.eveonline.com/oauth/verify/";

    // OAuth2 application configuration constants (application-dependent)
    // Secrets are system or properties-configured values (via -D on the command line or a props file)
    public static final String DRTTI_CCP_EVE_CONTACT_WITH_PROBLEMS = "jadecristal@gmail.com";
    public static final String DRTTI_CCP_EVE_SSO_OAUTH2_CALLBACK_URI = "https://drtti.io/ccp/eve/sso/";
    // public static String DRTTI_CCP_EVE_SSO_OAUTH2_CLIENT_ID = "";
    // public static String DRTTI_CCP_EVE_SSO_OAUTH2_CLIENT_SECRET = "";

    // OAuth2 flow constants
    public static final String EXCEPTION_OAUTH2_SYSTEM = "EXCEPTION_OAUTH2_SYSTEM";
    public static final String EXCEPTION_OAUTH2_PROBLEM = "EXCEPTION_OAUTH2_PROBLEM";

    public static String getClientId() {
        return System.getProperty("DRTTI_CLIENT_ID");
    }

    public static String getClientSecret() {
        return System.getProperty("DRTTI_CLIENT_SECRET");
    }

    private CcpEveSsoConfig() {
    }

}

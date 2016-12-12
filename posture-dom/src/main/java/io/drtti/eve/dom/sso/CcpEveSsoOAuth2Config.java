package io.drtti.eve.dom.sso;

/**
 * @author cwinebrenner
 */
public class CcpEveSsoOAuth2Config {

    // HttpSession variable
    // TODO: put these in a web config class or something
    public final static String DRTTI_EVE_CREDENTIAL_COOKIE_KEY = "CcpEveSsoOAuth2Credential";
    public final static String DRTTI_EVE_PILOT_NAME_COOKIE_KEY = "AuthenticatedPilotName";

    // OAuth2 authorize endpoint configuration constants (application-independent)
    public final static String CCP_EVE_SSO_OAUTH2_ENDPOINT_AUTH_URI = "https://login.eveonline.com/oauth/authorize/";
    public final static String CCP_EVE_SSO_OAUTH2_ENDPOINT_AUTH_RESPONSE_TYPE = "code";

    // OAuth2 token endpoint configuration constants (scopes application-dependent)
    public final static String CCP_EVE_SSO_OAUTH2_ENDPOINT_TOKEN_URI = "https://login.eveonline.com/oauth/token/";
    public final static String CCP_EVE_SSO_OAUTH2_SCOPES_CREST = "characterLocationRead";
    public final static String CCP_EVE_SSO_OAUTH2_SCOPES_ESI = "esi-location.read_location.v1";

    // OAuth2 application configuration constants (application-dependent)
    public final static String DRTTI_CCP_EVE_CONTACT_WITH_PROBLEMS = "jadecristal@gmail.com";
    public final static String DRTTI_CCP_EVE_SSO_OAUTH2_CLIENT_ID = "61925ed48911466bbc5f2072858bf2e0";
    public final static String DRTTI_CCP_EVE_SSO_OAUTH2_CLIENT_SECRET = "0DMac5ePX1dKYIMdG0k1xnxrP0I2oOL5qHNr4u0p";
    public final static String DRTTI_CCP_EVE_SSO_OAUTH2_CALLBACK_URI = "https://drtti.io/ccp/eve/sso/";

    // OAuth2 flow constants
    public final static String EXCEPTION_OAUTH2_SYSTEM = "EXCEPTION_OAUTH2_SYSTEM";
    public final static String EXCEPTION_OAUTH2_PROBLEM = "EXCEPTION_OAUTH2_PROBLEM";

    // OAuth2 verify endpoint
    public final static String CCP_EVE_SSO_OAUTH2_ENDPOINT_VERIFY = "https://login.eveonline.com/oauth/verify/";

}

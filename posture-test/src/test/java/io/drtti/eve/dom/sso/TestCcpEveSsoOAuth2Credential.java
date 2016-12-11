package io.drtti.eve.dom.sso;

import org.apache.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.*;

import java.lang.Thread;
import java.time.LocalDateTime;

/**
 * @author cwinebrenner
 */
public class TestCcpEveSsoOAuth2Credential {

    private final static String ACCESS_TOKEN = "ACCESS_TOKEN";
    private final static String TOKEN_TYPE = "TOKEN_TYPE";
    private final static Long EXPIRES_IN = 2L;
    private final static String REFRESH_TOKEN = "REFRESH_TOKEN";

    private final Logger log = Logger.getLogger(this.getClass());

    @Test
    public void testCredentialExpired() throws InterruptedException {

        CcpEveSsoOAuth2Credential tc = new CcpEveSsoOAuth2Credential(ACCESS_TOKEN, TOKEN_TYPE, EXPIRES_IN, REFRESH_TOKEN);
        log.info("Expiring expected in " + EXPIRES_IN + "s; sleeping " + (EXPIRES_IN+1) + "s");
        Thread.sleep((EXPIRES_IN+1)*1000);
        log.info("isExpired(): " + tc.isExpired());
        assertTrue(tc.isExpired());
    }

    @Test
    public void testCredentialDidNotExpire() throws InterruptedException {

        CcpEveSsoOAuth2Credential tc = new CcpEveSsoOAuth2Credential(ACCESS_TOKEN, TOKEN_TYPE, EXPIRES_IN, REFRESH_TOKEN);
        log.info("Expiring expected in " + EXPIRES_IN + "s; sleeping " + (EXPIRES_IN-1) + "s");
        Thread.sleep((EXPIRES_IN-1)*1000);
        log.info("isExpired(): " + tc.isExpired());
        assertFalse(tc.isExpired());
    }

}

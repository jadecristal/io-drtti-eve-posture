package io.drtti.eve.ejb.client;

import io.drtti.eve.dom.core.DrttiUser;
import io.drtti.eve.dom.sso.CcpEveSsoCredential;
import io.drtti.eve.ejb.sso.CcpEveSsoBean;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author cwinebrenner
 */
@Singleton
@Startup
public class DrttiUserRegistrationServiceBean {

    private Logger log = Logger.getLogger(this.getClass());

    @EJB
    CcpEveSsoBean ssoBean;

    private Set<DrttiUser> users;

    public DrttiUserRegistrationServiceBean() {
        this.users = new HashSet<>();
    }

    public void register(DrttiUser user) {
        users.add(user);
    }

    public void unregister(DrttiUser user) {
        if (users.contains(user)) {
            users.remove(user);
        }
    }

    public boolean isRegistered(DrttiUser user) {
        return users.contains(user);
    }

    public Set<DrttiUser> getRegisteredUsers() {
        return (!users.isEmpty() ? Collections.unmodifiableSet(users) : null);
    }

    @Schedule(hour = "*", minute = "*", second = "*/1")
    public void refreshRegisteredUsersExpiredCredentials() {

        if (users != null) {
            for (DrttiUser user : users) {
                if (user.getCredential().isExpired()) {
                    log.info("DrttiUserRegistrationService: Refreshing expired OAuth2 credential for " + user.getPilot().getCharacterName());
                    CcpEveSsoCredential refreshedCredential = ssoBean.refreshAccessToken(user.getCredential());
                    if (refreshedCredential != null) {
                        user.setCredential(refreshedCredential);
                    } else {
                        log.error("DrttiUserRegistrationService: FAILED refreshing expired OAuth2 credential for " + user.getPilot().getCharacterName());
                    }
                }
            }
        }

    }

}

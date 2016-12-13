package io.drtti.eve.ejb.location;

import io.drtti.eve.dom.ccp.SolarSystem;
import io.drtti.eve.dom.core.DrttiUser;
import io.drtti.eve.dom.esi.EsiCharacterLocation;
import io.drtti.eve.dom.esi.EsiSolarSystem;
import io.drtti.eve.dom.location.ReportedPilotLocation;
import io.drtti.eve.ejb.client.DrttiUserRegistrationServiceBean;
import io.drtti.eve.ejb.esi.CcpEveEsiBean;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.Set;

/**
 * Updates all registered (logged-in) user locations using ESI on a scheduled basis
 * // TODO: Setup an internal pool to back off some on users who have been in the same location for a certain time?
 *
 * @author cwinebrenner
 */
@Singleton
@Startup
public class DrttiUserLocationMonitorServiceBean {

    private final Logger log = Logger.getLogger(this.getClass());

    @EJB
    private DrttiUserRegistrationServiceBean dursBean;

    @EJB
    private CcpEveEsiBean esiBean;

    @EJB
    private PilotLocationServiceBean plsBean;

    @Schedule(hour = "*", minute = "*", second = "*/10")
    // Endpoint cached for up to 5 seconds, per ESI (https://esi.tech.ccp.is/latest/#!/Location/get_characters_character_id_location)
    public void refreshDrttiUsersLocationFromEsi() {

        Set<DrttiUser> registeredUsers = dursBean.getRegisteredUsers();


        if (registeredUsers != null) {

            for (DrttiUser user : registeredUsers) {

                EsiCharacterLocation esiCharacterLocation = esiBean.getEsiCharacterLocation(user.getCredential(), user.getPilot().getCharacterId());
                EsiSolarSystem esiSolarSystem = esiBean.getEsiSolarSystem(esiCharacterLocation.getSolarSystemId());
                SolarSystem userSolarSystem = new SolarSystem(esiCharacterLocation.getSolarSystemId(), esiSolarSystem.getSolarSystemName());
                plsBean.reportPilotLocation(new ReportedPilotLocation(user.getPilot(), userSolarSystem));
                log.info("UserLocationMonitoring: ESI found " + user.getPilot().getCharacterName() + " in " + userSolarSystem.getSolarSystemName());

            }

        }
    }

}

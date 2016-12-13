package io.drtti.eve.ejb.location;

import io.drtti.eve.dom.ccp.Pilot;
import io.drtti.eve.dom.ccp.SolarSystem;
import io.drtti.eve.dom.location.PilotLocation;
import io.drtti.eve.dom.location.ReportedPilotLocation;

import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cwinebrenner
 */
@Singleton
@Startup
public class PilotLocationService {

    private final Logger log = Logger.getLogger(this.getClass());

    @EJB
    PilotLocationStorageBean pls;

    private List<ReportedPilotLocation> rpls;
    // TODO: endpoint to receive updates on location?
    // TODO: get pilots in SolarSystem
    // TODO:FUTURE get pilots in Location

    @PostConstruct
    private void startup() {
        log.debug("PostConstruct fired; starting up");
        log.info("Initializing ReportedPilotLocation storage");
        rpls = new ArrayList<>();
    }

    @PreDestroy
    private void shutdown() {
        log.debug("PreDestroy fired; shutting down");
    }

    @Schedule(hour = "*", minute = "*", second = "*/10")
    private void reapExpiredLocations() {
        log.debug("@Scheduled: Reaping expired reported locations");
        for (ReportedPilotLocation rpl : rpls) {
            if (rpl.isExpired())  {
                log.debug("- Reported location of " + rpl.getPilot().getCharacterName() + " is expired; removing from storage");
                pls.clearPilotLocation(rpl.getPilot());
                rpls.remove(rpl);
            }
        }
    }

    public void reportPilotLocation(Pilot pilot, SolarSystem solarSystem) {
        log.info(pilot.getCharacterName() + " reported in " + solarSystem.getSolarSystemName());
        processReportedPilotLocation(new ReportedPilotLocation(pilot, solarSystem));
    }

    public void reportPilotLocation(PilotLocation pl) {
        log.info(pl.getPilot().getCharacterName() + " reported in " + pl.getSolarSystem().getSolarSystemName());
        processReportedPilotLocation(new ReportedPilotLocation(pl));
    }

    public void reportPilotLocation(ReportedPilotLocation rpl) {
        log.info(rpl.getPilot().getCharacterName() + " reported in " + rpl.getSolarSystem().getSolarSystemName());
        processReportedPilotLocation(rpl);
    }

    private void processReportedPilotLocation(ReportedPilotLocation reportedPilotLocation) {
        rpls.add(reportedPilotLocation);
        pls.setPilotLocation(reportedPilotLocation.getPilotLocation());
    }

}

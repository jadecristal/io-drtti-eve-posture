package io.drtti.eve.dom.location;

import io.drtti.eve.dom.ccp.Pilot;
import io.drtti.eve.dom.ccp.SolarSystem;
import io.drtti.eve.dom.core.ExpirableReport;

import java.time.LocalDateTime;

/**
 * @author cwinebrenner
 */
public class ReportedPilotLocation extends PilotLocation implements ExpirableReport {

    private final static int EXPIRATION_POLICY_SECONDS = 600;

    private LocalDateTime reportedTimeStamp;

    public ReportedPilotLocation() {
    }

    public ReportedPilotLocation(Pilot pilot, SolarSystem solarSystem) {
        super(pilot, solarSystem);
        this.reportedTimeStamp = LocalDateTime.now();
    }

    public ReportedPilotLocation(Pilot pilot, SolarSystem solarSystem, LocalDateTime reportedTimeStamp) {
        super(pilot, solarSystem);
        this.reportedTimeStamp = reportedTimeStamp;
    }

    public LocalDateTime getReportedTimeStamp() {
        return reportedTimeStamp;
    }

    public void setReportedTimeStamp(LocalDateTime reportedTimeStamp) {
        this.reportedTimeStamp = reportedTimeStamp;
    }

    public boolean isExpired() {
        // TODO: check the reported timeStamp + EXPIRATION_POLICY_SECONDS against the current date/time
        return false;
    }

}

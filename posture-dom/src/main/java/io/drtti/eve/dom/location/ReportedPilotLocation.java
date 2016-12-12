package io.drtti.eve.dom.location;

import io.drtti.eve.dom.ccp.Pilot;
import io.drtti.eve.dom.ccp.SolarSystem;
import io.drtti.eve.dom.core.ExpirableReport;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * @author cwinebrenner
 */
public class ReportedPilotLocation extends PilotLocation implements ExpirableReport {

    private final static int EXPIRATION_POLICY_SECONDS = 600;

    private Instant reportedTimeStamp;

    public ReportedPilotLocation() {
    }

    public ReportedPilotLocation(Pilot pilot, SolarSystem solarSystem) {
        super(pilot, solarSystem);
        this.reportedTimeStamp = Instant.now();
    }

    public ReportedPilotLocation(Pilot pilot, SolarSystem solarSystem, Instant reportedTimeStamp) {
        super(pilot, solarSystem);
        this.reportedTimeStamp = reportedTimeStamp;
    }

    public ReportedPilotLocation(PilotLocation pilotLocation) {
        super(pilotLocation.getPilot(), pilotLocation.getSolarSystem());
        this.reportedTimeStamp = Instant.now();
    }

    public PilotLocation getPilotLocation() {
        return new PilotLocation(this.getPilot(), this.getSolarSystem());
    }

    public Instant getReportedTimeStamp() {
        return reportedTimeStamp;
    }

    public void setReportedTimeStamp(Instant reportedTimeStamp) {
        this.reportedTimeStamp = reportedTimeStamp;
    }

    public boolean isExpired() {
        return (ChronoUnit.SECONDS.between(reportedTimeStamp, Instant.now()) > EXPIRATION_POLICY_SECONDS);
    }

}

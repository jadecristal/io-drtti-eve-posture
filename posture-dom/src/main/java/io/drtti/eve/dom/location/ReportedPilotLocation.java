package io.drtti.eve.dom.location;

import io.drtti.eve.dom.ccp.Pilot;
import io.drtti.eve.dom.ccp.SolarSystem;
import io.drtti.eve.dom.core.ExpirableReport;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * @author cwinebrenner
 */
public class ReportedPilotLocation extends PilotLocation implements ExpirableReport {

    private static final int EXPIRATION_POLICY_SECONDS = 15;

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

    // Comparison/hashing methods/boilerplate from Apache Commons
    // TODO: consider whether or not the Instant matters in equals/hashCode
    @Override
    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (o == this) { return true; }
        if (o.getClass() != getClass()) {
            return false;
        }
        ReportedPilotLocation rpl = (ReportedPilotLocation) o;
        return new EqualsBuilder()
                .append(this.getPilot().getCharacterId(), rpl.getPilot().getCharacterId())
                .append(this.getSolarSystem().getSolarSystemId(), rpl.getSolarSystem().getSolarSystemId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(637, 249)
                .append(this.getPilot().getCharacterId())
                .append(this.getSolarSystem().getSolarSystemId())
                .toHashCode();
    }

}

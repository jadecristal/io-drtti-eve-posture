package io.drtti.eve.dom.posture;

import io.drtti.eve.dom.ccp.Pilot;
import io.drtti.eve.dom.core.ExpirableReport;
import io.drtti.eve.dom.core.Posture;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * @author cwinebrenner
 */
public class ReportedPilotPosture extends PilotPosture implements ExpirableReport {

    private final static int EXPIRATION_POLICY_SECONDS = 300;

    private Instant reportedTimeStamp;

    public ReportedPilotPosture() {
    }

    public ReportedPilotPosture(Pilot pilot, Posture posture) {
        super(pilot, posture);
        this.reportedTimeStamp = Instant.now();
    }

    public ReportedPilotPosture(Pilot pilot, Posture posture, Instant reportedTimeStamp) {
        super(pilot, posture);
        this.reportedTimeStamp = reportedTimeStamp;
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

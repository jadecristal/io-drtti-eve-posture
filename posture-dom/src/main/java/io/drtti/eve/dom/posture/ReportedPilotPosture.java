package io.drtti.eve.dom.posture;

import io.drtti.eve.dom.ccp.Pilot;
import io.drtti.eve.dom.core.ExpirableReport;
import io.drtti.eve.dom.core.Posture;

import java.time.LocalDateTime;

/**
 * @author cwinebrenner
 */
public class ReportedPilotPosture extends PilotPosture implements ExpirableReport {

    private final static int EXPIRATION_POLICY_SECONDS = 300;

    private LocalDateTime reportedTimeStamp;

    public ReportedPilotPosture() {
    }

    public ReportedPilotPosture(Pilot pilot, Posture posture) {
        super(pilot, posture);
        this.reportedTimeStamp = LocalDateTime.now();
    }

    public ReportedPilotPosture(Pilot pilot, Posture posture, LocalDateTime reportedTimeStamp) {
        super(pilot, posture);
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

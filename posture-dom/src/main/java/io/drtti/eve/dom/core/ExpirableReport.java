package io.drtti.eve.dom.core;

import java.time.Instant;

/**
 * @author cwinebrenner
 */
public interface ExpirableReport {

    Instant getReportedTimeStamp();
    void setReportedTimeStamp(Instant reportedTimeStamp);
    boolean isExpired();

}

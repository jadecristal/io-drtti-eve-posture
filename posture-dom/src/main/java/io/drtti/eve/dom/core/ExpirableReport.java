package io.drtti.eve.dom.core;

import java.time.Instant;

/**
 * @author cwinebrenner
 */
public interface ExpirableReport {

    public Instant getReportedTimeStamp();
    public void setReportedTimeStamp(Instant reportedTimeStamp);
    public boolean isExpired();

}

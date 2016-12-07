package io.drtti.eve.dom.core;

import java.time.LocalDateTime;

/**
 * @author cwinebrenner
 */
public interface ExpirableReport {

    public LocalDateTime getReportedTimeStamp();
    public void setReportedTimeStamp(LocalDateTime reportedTimeStamp);
    public boolean isExpired();

}

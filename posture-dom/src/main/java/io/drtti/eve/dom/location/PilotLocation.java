package io.drtti.eve.dom.location;

import io.drtti.eve.dom.core.Pilot;
import io.drtti.eve.dom.core.SolarSystem;

import java.time.LocalDateTime;

/**
 * @author cwinebrenner
 */
public class PilotLocation {

    private Pilot pilot;
    private SolarSystem solarSystem;
    private LocalDateTime timeStamp;

    public Pilot getPilot() {
        return pilot;
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    public SolarSystem getSolarSystem() {
        return solarSystem;
    }

    public void setSolarSystem(SolarSystem solarSystem) {
        this.solarSystem = solarSystem;
    }

}

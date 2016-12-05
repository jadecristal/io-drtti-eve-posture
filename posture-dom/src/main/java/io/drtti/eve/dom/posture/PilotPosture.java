package io.drtti.eve.dom.posture;

import io.drtti.eve.dom.core.Pilot;

/**
 * @author cwinebrenner
 */
public class PilotPosture {

    private Pilot pilot;
    private Posture posture;

    public Pilot getPilot() {
        return pilot;
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    public Posture getPosture() {
        return posture;
    }

    public void setPosture(Posture posture) {
        this.posture = posture;
    }

}

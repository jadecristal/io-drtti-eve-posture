package io.drtti.eve.dom.posture;

import io.drtti.eve.dom.ccp.Pilot;
import io.drtti.eve.dom.core.Posture;

/**
 * Posture applied to a Pilot
 * @author cwinebrenner
 */
public class PilotPosture {

    private Pilot pilot;
    private Posture posture;

    public PilotPosture() {
    }

    public PilotPosture(Pilot pilot, Posture posture) {
        this.pilot = pilot;
        this.posture = posture;
    }

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

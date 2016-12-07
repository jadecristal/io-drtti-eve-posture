package io.drtti.eve.dom.core;

/**
 * Dictionary definition: attitude a person or group has towards a subject.
 * Whether some subject (e.g. Pilot, Corporation, Alliance) is liked or not.
 * @author cwinebrenner
 */
public enum Posture {

    GOOD(true) {
        @Override
        public String toString() {
            return "GOOD: Subject is agreeable/pleasant/benevolent.";
        }
    },
    BAD(false) {
        @Override
        public String toString() {
            return "BAD: Subject is unfavorable/morally objectionable/injurious/harmful.";
        }
    };

    private boolean postureValue;

    private Posture(boolean postureValue) {
        this.postureValue = postureValue;
    }

    public boolean isGood() {
        return postureValue;
    }

    public boolean isBad() {
        return !postureValue;
    }

}

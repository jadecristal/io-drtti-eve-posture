package io.drtti.eve.dom.standing;

/**
 * @author cwinebrenner
 */
public enum CcpEveStanding {

    BLUE(10) {
        @Override
        public String toString() {
            return "BLUE: Standings at +10";
        }
    },
    LIGHT_BLUE(5) {
        @Override
        public String toString() {
            return "LIGHT BLUE: Standings at least +5";
        }
    },
    NEUTRAL(0) {
        @Override
        public String toString() {
            return "NEUTRAL: Derived standings explicitly 0";
        }
    },
    LIGHT_RED(-5) {
        @Override
        public String toString() {
            return "LIGHT RED: Standings at least -5";
        }
    },
    RED(-10) {
        @Override
        public String toString() {
            return "RED: Standings at -10";
        }
    };

    private int standingValue;

    private CcpEveStanding(int standingValue) {
        this.standingValue = standingValue;
    }

    public int getStandingValue() {
        return standingValue;
    }

}
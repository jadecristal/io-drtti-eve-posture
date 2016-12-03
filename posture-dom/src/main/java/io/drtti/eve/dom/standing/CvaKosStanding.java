package io.drtti.eve.dom.standing;

/**
 * @author cwinebrenner
 */
public enum CvaKosStanding {

    KOS_BY_LAST(-2) {
        @Override
        public String toString() {
            return "KOS_BY_LAST: Kill On Sight";
        }
    },
    KOS(-1) {
        @Override
        public String toString() {
            return "KOS: Kill On Sight";
        }
    },
    NEUTRAL(0) {
        @Override
        public String toString() {
            return "NEUTRAL: No data, not KOS yet...";
        }
    },
    NOT_KOS(1) {
        @Override
        public String toString() {
            return "NOT_KOS: Data, not set KOS";
        }
    },
    NOT_KOS_POSITIVE(2) {
        @Override
        public String toString() {
            return "NOT_KOS_POSITIVE: Derived positive standings";
        }
    };

    private int standingValue;

    private CvaKosStanding(int standingValue) {
        this.standingValue = standingValue;
    }

    public int getStandingValue() {
        return standingValue;
    }

}
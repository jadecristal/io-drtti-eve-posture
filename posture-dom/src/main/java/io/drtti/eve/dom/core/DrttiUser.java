package io.drtti.eve.dom.core;

import io.drtti.eve.dom.ccp.Pilot;
import io.drtti.eve.dom.sso.CcpEveSsoCredential;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author cwinebrenner
 */
//@Named
//@SessionScoped
public class DrttiUser {

    private Pilot pilot;
    private CcpEveSsoCredential credential;

    public DrttiUser() {
    }

    public Pilot getPilot() {
        return pilot;
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    public CcpEveSsoCredential getCredential() {
        return credential;
    }

    public void setCredential(CcpEveSsoCredential credential) {
        this.credential = credential;
    }

    // Comparison/hashing methods/boilerplate from Apache Commons
    @Override
    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (o == this) { return true; }
        if (o.getClass() != getClass()) {
            return false;
        }
        DrttiUser du = (DrttiUser) o;
        return new EqualsBuilder()
                .append(pilot.getCharacterId(), du.getPilot().getCharacterId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(365, 231)
                .append(pilot.getCharacterId())
                .toHashCode();
    }

}

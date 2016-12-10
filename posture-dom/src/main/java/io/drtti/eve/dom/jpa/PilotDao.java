package io.drtti.eve.dom.jpa;

import io.drtti.eve.dom.ccp.Pilot;

/**
 * @author cwinebrenner
 */
public class PilotDao extends GenericJpaDao {

    public PilotDao() {
        super(Pilot.class);
    }

}

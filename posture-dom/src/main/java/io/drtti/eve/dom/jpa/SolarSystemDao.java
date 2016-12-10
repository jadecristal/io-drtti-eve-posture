package io.drtti.eve.dom.jpa;

import io.drtti.eve.dom.ccp.SolarSystem;

/**
 * @author cwinebrenner
 */
public class SolarSystemDao extends GenericJpaDao {

    public SolarSystemDao() {
        super(SolarSystem.class);
    }

}

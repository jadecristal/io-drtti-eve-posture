package io.drtti.eve.dom.jpa;

import io.drtti.eve.dom.ccp.Pilot;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author cwinebrenner
 */
public class PilotDao extends GenericJpaDao {

    public PilotDao() {
        super(Pilot.class);
    }

    public void configureForTest(String persistenceUnit) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
        this.em = emf.createEntityManager();
    }

    public void completeTest() {
    }

}

package io.drtti.eve.dom.core;

import io.drtti.eve.dom.core.*;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.persistence.*;

/**
 * @author cwinebrenner
 */
public class TestHibernateJpaEntities {

    private static final String PERSISTENCE_UNIT = "drtti_eve";

    private static EntityManagerFactory emf;
    private static EntityManager em;

    @BeforeClass
    public static void setupJpaPersistenceFactory() {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        em = emf.createEntityManager();
    }
    // TODO: Something to test something here
}

package io.drtti.eve.dom.jpa;

import io.drtti.eve.dom.ccp.Pilot;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 * @author cwinebrenner
 */
@RunWith(Arquillian.class)
public class PilotDaoTest {

    @PersistenceContext(unitName = "drtti-eve-test")
    private EntityManager em;

    @Inject
    UserTransaction utx;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "asdf.war")
                .addPackage(PilotDaoTest.class.getPackage())
                .addPackage(PilotDao.class.getPackage())
                .addPackage(Pilot.class.getPackage())
                .addAsResource("META-INF/persistence.xml");
    }

    @Before
    public void preTest() throws Exception {
        utx.begin();
        em.joinTransaction();
    }

    @Test
    public void testCreatePilot() {
        //pilotDao.persist(new Pilot(92036750L, "Night Stalker321"));
        //em.persist(new Pilot(92036750L, "Night Stalker321"));

        //assertEquals("Night Stalker321", em.find(Pilot.class, 1L));
        assertNotNull(em);
    }

    @After
    public void postTest() throws Exception {
        utx.commit();
    }

}

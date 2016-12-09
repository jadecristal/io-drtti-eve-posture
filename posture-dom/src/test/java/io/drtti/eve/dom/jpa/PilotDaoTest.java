package io.drtti.eve.dom.jpa;

import io.drtti.eve.dom.ccp.Pilot;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author cwinebrenner
 */
public class PilotDaoTest {

    private final static Logger log = Logger.getLogger(PilotDaoTest.class);

    private final static String PERSISTENCE_UNIT = "drtti-eve-test";
    private static PilotDao pilotDao;

    @BeforeClass
    public static void preconfig() {
        log.setLevel(Level.INFO);
        pilotDao = new PilotDao();
        pilotDao.configureForTest(PERSISTENCE_UNIT);
    }

    @Test
    public void testCreatePilot() {
        Pilot matos = new Pilot();
        matos.setCharacterId(92036750L);
        matos.setCharacterName("Night Stalker321");
        pilotDao.persist(matos);

    }

}

package io.drtti.eve.ejb.location;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

/**
 * @author cwinebrenner
 */
public class TestPilotLocationStorageInit {

    private final static Logger log = Logger.getLogger(TestPilotLocationStorageInit.class);

    private final static String EJB_POST_CONSTRUCT_METHOD = "startup";
    private final static String EJB_PRE_DESTROY_METHOD = "shutdown";

    private static PilotLocationStorageBean pls;

    // Directly use a noted method name rather than fully scanning the class with reflection to find @PostConstruct
    // Scan for annotations like: http://stackoverflow.com/questions/6593597/java-seek-a-method-with-specific-annotation-and-its-annotation-element
    @BeforeClass
    public static void initPilotLocationStorage() throws Exception {

        log.info("Creating instance of EJB PilotLocationStorageBean for initialization testing...");
        pls = new PilotLocationStorageBean();

        log.info("Simulating @PostConstruct...");
        log.debug("- Locating specified @PostConstruct method '" + EJB_POST_CONSTRUCT_METHOD + "'...");
        Class<PilotLocationStorageBean> clazz = PilotLocationStorageBean.class;
        Method plsEjbPostConstruct = clazz.getDeclaredMethod(EJB_POST_CONSTRUCT_METHOD);
        log.debug("- Setting private @PostConstruct method accessible...");
        plsEjbPostConstruct.setAccessible(true);
        log.info("- Invoking @PostConstruct method '" + EJB_POST_CONSTRUCT_METHOD + "' offline in test container...");
        plsEjbPostConstruct.invoke(pls);
    }

    @Test
    public void testPlsPilotsEmpty() {
        log.info("Testing Pilot:SolarSystem Map exists and is empty...");

        assertNotNull(pls.getPilotCount());
        assertEquals(0, pls.getPilotCount());
    }

}

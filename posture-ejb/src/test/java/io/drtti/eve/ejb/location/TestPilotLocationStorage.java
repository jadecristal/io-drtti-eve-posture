package io.drtti.eve.ejb.location;

import io.drtti.eve.dom.ccp.Pilot;
import io.drtti.eve.dom.ccp.SolarSystem;
import io.drtti.eve.dom.location.PilotLocation;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

/**
 * @author cwinebrenner
 */
public class TestPilotLocationStorage {

    private final static Logger log = Logger.getLogger(TestPilotLocationStorage.class);

    private final static String EJB_POST_CONSTRUCT_METHOD = "startup";
    private final static String EJB_PRE_DESTROY_METHOD = "shutdown";

    private final static String CCP_EVE_PILOT_NAME = "Cale Cloudstrike";
    private final static String CCP_EVE_SOLAR_SYSTEM_HUB = "Jita";
    private final static String CCP_EVE_SOLAR_SYSTEM_HOME = "G-B22J";

    private static PilotLocationStorage pls;
    private static Pilot pilot;
    private static SolarSystem solarSystem;
    private static PilotLocation pilotLocation;

    // Directly use a noted method name rather than fully scanning the class with reflection to find @PostConstruct
    @BeforeClass
    public static void initPilotLocationStorage() throws Exception {
        log.info("Creating instance of EJB PilotLocationStorage for testing...");

        pls = new PilotLocationStorage();

        log.info("Simulating @PostConstruct in-container initialization...");
        Class<PilotLocationStorage> clazz = PilotLocationStorage.class;
        Method plsEjbPostConstruct = clazz.getDeclaredMethod(EJB_POST_CONSTRUCT_METHOD);
        plsEjbPostConstruct.setAccessible(true);
        plsEjbPostConstruct.invoke(pls);
    }

    @Before
    public void initPilotLocationStorageSupportingObjects() {
        pilot = new Pilot(CCP_EVE_PILOT_NAME);
        solarSystem = new SolarSystem(CCP_EVE_SOLAR_SYSTEM_HUB);
        pilotLocation = new PilotLocation(pilot, solarSystem);
    }

    @Test
    public void testPlsSetGetLocationDirect() {
        log.info("Testing setting Pilot location and retrieving set location...");

        pls.setPilotLocation(pilot, solarSystem);

        assertEquals(pls.getPilotLocation(pilot).getSolarSystem().getSolarSystemName(), CCP_EVE_SOLAR_SYSTEM_HUB);
    }

    @Test
    public void testPlsSetGetLocationObject() {
        log.info("Testing setting Pilot location using PilotLocation and retrieving set location...");

        pls.setPilotLocation(pilotLocation);

        assertEquals(pls.getPilotLocation(pilot).getSolarSystem().getSolarSystemName(), CCP_EVE_SOLAR_SYSTEM_HUB);
    }

    @Test
    public void testPlsSetAndChangeLocation() {
        log.info("Testing setting Pilot location, changing it, and retrieving new location...");

        pls.setPilotLocation(pilot, solarSystem);
        pls.setPilotLocation(pilot, new SolarSystem(CCP_EVE_SOLAR_SYSTEM_HOME));

        assertEquals(pls.getPilotLocation(pilot).getSolarSystem().getSolarSystemName(), CCP_EVE_SOLAR_SYSTEM_HOME);
    }

}

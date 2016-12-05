package io.drtti.eve.ejb.posture;

import io.drtti.eve.dom.core.Pilot;
import io.drtti.eve.dom.posture.Posture;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.HashMap;

/**
 * @author cwinebrenner
 */
@Singleton
@Startup
public class PilotPostureStorage {

    private HashMap<Pilot, Posture> pilotPosture;

    @PostConstruct
    private void postureStorageStartup() {
        // TODO:FUTURE load all unexpired pilot postures from JPA
    }

    @PreDestroy
    private void postureStorageShutdown() {
        // TODO:FUTURE persist all unexpired pilot postures to JPA
    }

}

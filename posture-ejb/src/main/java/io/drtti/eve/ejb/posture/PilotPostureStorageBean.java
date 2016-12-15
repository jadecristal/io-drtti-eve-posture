package io.drtti.eve.ejb.posture;

import io.drtti.eve.dom.ccp.Pilot;
import io.drtti.eve.dom.core.Posture;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.Map;

/**
 * @author cwinebrenner
 */
@Singleton
@Startup
public class PilotPostureStorageBean {

    private Map<Pilot, Posture> pilotPosture;

    @PostConstruct
    private void postureStorageStartup() {
        // TODO:FUTURE load all unexpired pilot postures from JPA
    }

    @PreDestroy
    private void postureStorageShutdown() {
        // TODO:FUTURE persist all unexpired pilot postures to JPA
    }

}

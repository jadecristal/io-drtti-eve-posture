package io.drtti.eve.ejb.location;

import io.drtti.eve.dom.core.Pilot;
import io.drtti.eve.dom.core.SolarSystem;
import io.drtti.eve.dom.location.PilotLocation;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author cwinebrenner
 */
@Singleton
@Startup
public class PilotLocationStorage {

    private HashMap<SolarSystem, HashSet<Pilot>> systemPilots;
    private HashMap<Pilot, SolarSystem> pilotSystem;

    @PostConstruct
    public void positionStorageStartup() {
        systemPilots = new HashMap<>();
        pilotSystem = new HashMap<>();
        // TODO:load all solar systems from JPA
        // TODO:FUTURE load all unexpired pilot positions from JPA
        // TODO:if @PostConstruct methods are really public public then add defensive code
    }

    @PreDestroy
    public void positionStorageShutdown() {
        // TODO:FUTURE persist all unexpired pilot positions to JPA
        // TODO:if @PreDestroy methods are really public public then add defensive code
    }

    /**
     * Returns a PilotPosition referencing the supplied Pilot,
     * with either a SolarSystem location or null if the pilot
     * was not found in storage.
     * @param pilot Pilot to get the location of
     * @return A PilotPosition containing the location of the Pilot if found, or null
     */
    public PilotLocation getPilotLocation(Pilot pilot) {
        PilotLocation pilotInSystem = new PilotLocation();
        pilotInSystem.setPilot(pilot);
        pilotInSystem.setSolarSystem(pilotSystem.get(pilot));
        return pilotInSystem;
    }

    /**
     * Returns a Set of PilotLocations with locations of all Pilots
     * provided in the Set pilots, with each PilotPosition containing
     * either a SolarSystem location or null if the pilot was not
     * found in storage.
     * @param pilots Set of Pilots to get locations of
     * @return A Set of PilotPositions containing the location of each Pilot if found, or null
     */
    public Set<PilotLocation> getPilotLocations(Set<Pilot> pilots) {
        Set<PilotLocation> pilotLocations = new HashSet<>();

        for (Pilot pilot : pilots) {
            pilotLocations.add(getPilotLocation(pilot));
        }

        return pilotLocations;
    }

    /**
     * Sets the location of a single Pilot in storage, making sure to
     * properly initialize any new SolarSystem not previously recorded,
     * as well as remove any contradictory location data.
     * This is the base level location updater with all the Collection
     * logic included.
     * @param pilot Pilot to store the location of
     * @param solarSystem SolarSystem to store the provided Pilot in
     */
    public void setPilotLocation(Pilot pilot, SolarSystem solarSystem) {

        // if we know where the pilot is now, remove them from that location
        if (pilotSystem.containsKey(pilot)) {
            systemPilots.get(pilotSystem.get(pilot)).remove(pilot);
        }

        // if the solar system the pilot is now is in new to us, initialize it
        if (!systemPilots.containsKey(solarSystem)) {
            systemPilots.put(solarSystem, new HashSet<>());
        }

        // the solar system the pilot is in was there or initialized; add them
        systemPilots.get(solarSystem).add(pilot);

        // pilot can only be one place; add them there (overwrites if present)
        pilotSystem.put(pilot, solarSystem);

    }

    /**
     * Convenience method to set Pilot location from a PilotLocation.
     * Will not update location if the provided PilotLocation has a null
     * Pilot or SolarSystem.
     * @param pilotLocation PilotLocation containing details of a Pilot's location to store
     */
    public void setPilotLocation(PilotLocation pilotLocation) {
        if ((pilotLocation.getPilot() != null) && (pilotLocation.getSolarSystem() != null))  {
            setPilotLocation(pilotLocation.getPilot(), pilotLocation.getSolarSystem());
        }
    }

    /**
     * Updates and/or adds locations for a Set of Pilots to a provided SolarSystem.
     * @param pilots Set of Pilots to update into the provided location
     * @param solarSystem SolarSystem in which the provided pilots should be stored
     */
    public void setPilotsLocation(Set<Pilot> pilots, SolarSystem solarSystem) {

        // walk provided list of pilots and set the location of each
        for (Pilot pilot : pilots) {
            setPilotLocation(pilot, solarSystem);
        }
    }

}

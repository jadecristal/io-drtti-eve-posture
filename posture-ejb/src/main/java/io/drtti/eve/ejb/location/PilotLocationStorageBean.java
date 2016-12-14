package io.drtti.eve.ejb.location;

import io.drtti.eve.dom.ccp.Pilot;
import io.drtti.eve.dom.ccp.SolarSystem;
import io.drtti.eve.dom.location.PilotLocation;

import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import java.util.*;

/**
 * @author cwinebrenner
 */
@Singleton
@Startup
public class PilotLocationStorageBean {

    private final Logger log = Logger.getLogger(this.getClass());

    private HashMap<SolarSystem, HashSet<Pilot>> systemPilots;
    private HashMap<Pilot, SolarSystem> pilotSystem;

    @PostConstruct
    private void startup() {
        systemPilots = new HashMap<>();
        pilotSystem = new HashMap<>();
        // TODO:load all solar systems from JPA
        // TODO:FUTURE load all unexpired pilot positions from JPA
    }

    @PreDestroy
    private void shutdown() {
        // TODO:FUTURE persist all unexpired pilot positions to JPA
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

        log.debug("Requested set location of Pilot: " + pilot.getCharacterName() + " in SolarSystem: " + solarSystem.getSolarSystemName());
        log.info("Setting location of Pilot: " + pilot.getCharacterName() + " to SolarSystem: " + solarSystem.getSolarSystemName());

        // if we know where the pilot is now, remove them from that location
        if (pilotSystem.containsKey(pilot)) {
            log.debug("- Found Pilot in SolarSystem: " + pilotSystem.get(pilot).getSolarSystemName());
            systemPilots.get(pilotSystem.get(pilot)).remove(pilot);
            log.debug("- Removed Pilot from SolarSystem:Pilots Map (previously reported location)");
        }

        // if the solar system the pilot is now is in new to us, initialize it
        if (!systemPilots.containsKey(solarSystem)) {
            log.debug("- SolarSystem: " + solarSystem.getSolarSystemName() + " not present in storage; initializing backing HashSet");
            systemPilots.put(solarSystem, new HashSet<>());
        }

        // the solar system the pilot is in was there or initialized; add them
        systemPilots.get(solarSystem).add(pilot);
        log.debug("- Added Pilot to SolarSystem:Pilots Map");

        // pilot can only be one place; add them there (overwrites if present)
        pilotSystem.put(pilot, solarSystem);
        log.debug("- Added Pilot to Pilot:SolarSystem Map");

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

    /**
     * Removes the location of a Pilot from all in-memory storage.
     * @param pilot Pilot to forget the location of
     */
    public void clearPilotLocation(Pilot pilot) {

        log.debug("Requested clearing location of Pilot: " + pilot.getCharacterName());
        log.info("Clearing location of Pilot: " + pilot.getCharacterName());

        // if we know where the pilot is now, remove them from that location,
        // then remove them from the
        if (pilotSystem.containsKey(pilot)) {
            log.debug("- Found Pilot in SolarSystem: " + pilotSystem.get(pilot).getSolarSystemName());
            systemPilots.get(pilotSystem.get(pilot)).remove(pilot);
            log.debug("- Removed Pilot from SolarSystem:Pilots Map");
            pilotSystem.remove(pilot);
            log.debug("- Removed Pilot from Pilot:SolarSystem Map");
        }

    }

    /**
     * Returns how many Pilots are currently being tracked.
     * @return An int with the number of Pilots in the Pilot:SolarSystem Map
     */
    public int getPilotCount() {
        return pilotSystem.size();
    }

}

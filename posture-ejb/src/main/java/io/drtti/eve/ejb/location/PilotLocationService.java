package io.drtti.eve.ejb.location;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @author cwinebrenner
 */
@Stateless
public class PilotLocationService {

    @EJB PilotLocationStorage pls;

    // TODO: endpoint to receive updates on location?
    // TODO: get pilots in SolarSystem
    // TODO:FUTURE get pilots in Location

}

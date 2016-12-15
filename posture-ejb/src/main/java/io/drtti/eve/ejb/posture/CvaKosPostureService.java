package io.drtti.eve.ejb.posture;

import io.drtti.eve.dom.ccp.Pilot;
import io.drtti.eve.dom.core.Posture;
import io.drtti.eve.dom.cva.CvaKosResponse;
import io.drtti.eve.dom.cva.CvaKosResultItem;
import io.drtti.eve.dom.posture.*;
import io.drtti.eve.dom.standing.CvaKosStanding;

import com.fasterxml.jackson.databind.ObjectMapper;


import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;

import java.io.IOException;
import java.util.List;

/**
 * @author cwinebrenner
 */
@Stateless
public class CvaKosPostureService {

    private static final String CVA_KOS_BASE_URI = "http://kos.cva-eve.org/api/";
    private static final String CVA_KOS_MODE_SINGLE = "?c=json&type=unit&q=";
    private static final String CVA_KOS_MODE_MULTI = "?c=json&type=multi&q=";

    private Client cvaKosClient;
    private ObjectMapper cvaKosMapper;

    public CvaKosPostureService() {
    }

    @PostConstruct
    private void startup() {
        cvaKosClient = ClientBuilder.newClient();
        cvaKosMapper = new ObjectMapper();
    }

    public ReportedPilotPosture getPosture(Pilot pilot) {
        return new ReportedPilotPosture(new Pilot(), Posture.GOOD);
    }

    private void getCvaKosStatus(Pilot pilot) throws IOException {
        WebTarget cvaKosWebTarget = cvaKosClient.target(CVA_KOS_BASE_URI + CVA_KOS_MODE_SINGLE + pilot.getCharacterName());
        String cvaKosResponseJson = cvaKosWebTarget.request(MediaType.APPLICATION_JSON).get(String.class);

        CvaKosResponse cvaKosResponse = cvaKosMapper.readValue(cvaKosResponseJson, CvaKosResponse.class);
    }

    private void getCvaKosStatus(List<Pilot> pilots) throws IOException {
        // TODO: implement multi-pilot calls (permits 10 maximum names for q=, comma-separated)
    }

    private CvaKosStanding resolveCvaKosResultItem(CvaKosResultItem cvaKosResultItem) {
        // TODO: examine a cvaKosResultItem and return a CvaKosStanding value
        return CvaKosStanding.NEUTRAL;
    }

}

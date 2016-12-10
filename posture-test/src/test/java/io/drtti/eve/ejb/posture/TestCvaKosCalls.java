package io.drtti.eve.ejb.posture;

import io.drtti.eve.dom.cva.*;

import org.apache.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;

/**
 * @author cwinebrenner
 */
public class TestCvaKosCalls {

    private final static Logger log = Logger.getLogger(TestCvaKosCalls.class);

    @Test
    public void testUnitCvaKosCall() throws Exception {
        Client cvaKosClient = ClientBuilder.newClient();
        WebTarget cvaKosWebTarget = cvaKosClient.target("http://kos.cva-eve.org/api/?c=json&type=unit&q=Cale+Cloudstrike");
        String cvaKosResponseJson = cvaKosWebTarget.request(MediaType.APPLICATION_JSON).get(String.class);

        ObjectMapper cvaKosMapper = new ObjectMapper();
        CvaKosResponse cvaKosResponse = cvaKosMapper.readValue(cvaKosResponseJson, CvaKosResponse.class);

        CvaKosResultItem kosResultItem = cvaKosResponse.getResults().get(0);

        log.info(cvaKosMapper.writerWithDefaultPrettyPrinter().writeValueAsString(cvaKosResponse));
        assertEquals("CVA KOS: 'unit' Response not 'OK'", "OK", cvaKosResponse.getMessage());
        assertEquals("CVA KOS: Failed 'unit' KOS checking Cale Cloudstrike!", "Cale Cloudstrike", kosResultItem.getLabel());
    }

    @Test
    public void testMultiCvaKosCall() throws Exception {
        Client cvaKosClient = ClientBuilder.newClient();
        WebTarget cvaKosWebTarget = cvaKosClient.target("http://kos.cva-eve.org/api/?c=json&type=multi&q=Cale+Cloudstrike,Skye+Cloudstrike");
        String cvaKosResponseJson = cvaKosWebTarget.request(MediaType.APPLICATION_JSON).get(String.class);

        ObjectMapper cvaKosMapper = new ObjectMapper();
        CvaKosResponse cvaKosResponse = cvaKosMapper.readValue(cvaKosResponseJson, CvaKosResponse.class);

        log.info(cvaKosMapper.writerWithDefaultPrettyPrinter().writeValueAsString(cvaKosResponse));

        CvaKosResultItem kosResultItem = cvaKosResponse.getResults().get(1);
        assertEquals("CVA KOS: 'multi' Response not 'OK'", "OK", cvaKosResponse.getMessage());
        assertEquals("CVA KOS: Failed 'multi' KOS checking Skye Cloudstrike!", "Skye Cloudstrike", kosResultItem.getLabel());
    }

    @Test
    public void testUnitCvaKosCorpCall() throws Exception {
        Client cvaKosClient = ClientBuilder.newClient();
        WebTarget cvaKosWebTarget = cvaKosClient.target("http://kos.cva-eve.org/api/?c=json&type=unit&q=C.F");
        String cvaKosResponseJson = cvaKosWebTarget.request(MediaType.APPLICATION_JSON).get(String.class);

        ObjectMapper cvaKosMapper = new ObjectMapper();
        CvaKosResponse cvaKosResponse = cvaKosMapper.readValue(cvaKosResponseJson, CvaKosResponse.class);

        CvaKosResultItem kosResultItem = cvaKosResponse.getResults().get(0);

        log.info(cvaKosMapper.writerWithDefaultPrettyPrinter().writeValueAsString(cvaKosResponse));
        assertTrue(true);
    }

    @Test
    public void testUnitCvaKosAllianceCall() throws Exception {
        Client cvaKosClient = ClientBuilder.newClient();
        WebTarget cvaKosWebTarget = cvaKosClient.target("http://kos.cva-eve.org/api/?c=json&type=unit&q=Care+Factor");
        String cvaKosResponseJson = cvaKosWebTarget.request(MediaType.APPLICATION_JSON).get(String.class);

        ObjectMapper cvaKosMapper = new ObjectMapper();
        CvaKosResponse cvaKosResponse = cvaKosMapper.readValue(cvaKosResponseJson, CvaKosResponse.class);

        CvaKosResultItem kosResultItem = cvaKosResponse.getResults().get(0);

        log.info(cvaKosMapper.writerWithDefaultPrettyPrinter().writeValueAsString(cvaKosResponse));
        assertTrue(true);
    }

}

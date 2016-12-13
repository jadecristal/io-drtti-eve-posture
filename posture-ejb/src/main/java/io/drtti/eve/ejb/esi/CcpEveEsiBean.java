package io.drtti.eve.ejb.esi;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.drtti.eve.dom.ccp.SolarSystem;
import io.drtti.eve.dom.esi.EsiCharacterLocation;
import io.drtti.eve.dom.esi.EsiSolarSystem;
import io.drtti.eve.dom.jpa.SolarSystemBean;
import io.drtti.eve.dom.sso.CcpEveSsoCredential;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.client.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * @author cwinebrenner
 */
@Stateless
public class CcpEveEsiBean {

    private final Logger log = Logger.getLogger(this.getClass());

    private final static String ESI_BASE = "https://esi.tech.ccp.is/latest";

    private ObjectMapper om;

    @PersistenceContext
    EntityManager em;

    @EJB SolarSystemBean ssb;

    public CcpEveEsiBean() {
        om = new ObjectMapper();
    }

    public EsiCharacterLocation getCharacterLocation(CcpEveSsoCredential credential, Long characterId) {
        try {
            String esiResponseJson = esiCallAuthenticated(esiLocationEndpointBuilder(characterId), credential);
            EsiCharacterLocation esiCharacterLocation = om.readValue(esiResponseJson, EsiCharacterLocation.class);
            return esiCharacterLocation;
        } catch (IOException ioe) {
            log.error(ioe);
            return null;
        }
    }

    public EsiSolarSystem getSolarSystem(Long solarSystemId) {

        // TODO: JPA DAO UNFUN
        try {

            SolarSystem cachedSolarSystem = ssb.getByCcpEveId(solarSystemId);

//            CriteriaBuilder cb = em.getCriteriaBuilder();
//            CriteriaQuery cq = cb.createQuery();
//
//            Root<SolarSystem> s = cq.from(SolarSystem.class);
//            cq.where(cb.equal(s.get("solarSystemId"), solarSystemId));  // class.field
//            cq.select(s).where(cb.equal(s.get("solarSystemId"), solarSystemId));
//
//            TypedQuery<SolarSystem> tq = em.createQuery(cq);
//            cachedSolarSystem = tq.getSingleResult();

            return new EsiSolarSystem(cachedSolarSystem.getSolarSystemName());

        } catch (NoResultException nre) {
            log.info("SSB_NRE_EXCEPTION");
            log.info(nre);

            try {
                // look up SolarSystem, persist it, and return it
                String esiResponseJson = esiCall(esiSolarSystemEndpointBuilder(solarSystemId));
                EsiSolarSystem esiSolarSystem = om.readValue(esiResponseJson, EsiSolarSystem.class);

                em.persist(new SolarSystem(solarSystemId, esiSolarSystem.getSolarSystemName()));
                return esiSolarSystem;

            } catch (IOException ioe) {
                log.error(ioe);
                return null;
            }

        }
    }

    private String esiLocationEndpointBuilder(Long characterId) {
        return ESI_BASE + "/characters/" + characterId + "/location/?datasource=tranquility";
    }

    private String esiSolarSystemEndpointBuilder(Long solarSystemId) {
        return ESI_BASE + "/universe/systems/" + solarSystemId + "/?datasource=tranquility";
    }

    private String esiCall(String esiUri) {
        Client esiClient = ClientBuilder.newClient();
        WebTarget esiTarget = esiClient.target(esiUri);
        return esiTarget.request(MediaType.APPLICATION_JSON)
                .get(String.class);
    }

    private String esiCallAuthenticated(String esiUri, CcpEveSsoCredential credential) {
        Client esiClient = ClientBuilder.newClient();
        WebTarget esiTarget = esiClient.target(esiUri);
        return esiTarget.request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, credential.getHttpAuthorizationHeader())
                .get(String.class);
    }

}

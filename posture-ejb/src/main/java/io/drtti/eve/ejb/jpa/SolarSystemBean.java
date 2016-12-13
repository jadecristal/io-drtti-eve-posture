package io.drtti.eve.ejb.jpa;

import io.drtti.eve.dom.ccp.SolarSystem;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * @author cwinebrenner
 */
@Stateless
public class SolarSystemBean {

    private final Logger log = Logger.getLogger(this.getClass());

    private final static String CCP_EVE_ID_CLASS_FIELD = "solarSystemId";

    @PersistenceContext
    EntityManager em;

    public SolarSystem getById(Long id) {
        try {
            log.info("JPA DAO: Searching for SolarSystem with ID: " + id);
            return em.find(SolarSystem.class, id);
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    public SolarSystem getByCcpEveId(Long ccpEveId) {
        try {
            log.info("JPA DAO: Searching for SolarSystem with CCP EVE ID: " + ccpEveId);
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery();

            Root<SolarSystem> r = cq.from(SolarSystem.class);
            cq.select(r).where(cb.equal(r.get(CCP_EVE_ID_CLASS_FIELD), ccpEveId));

            TypedQuery<SolarSystem> tq = em.createQuery(cq);
            return tq.getSingleResult();
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    public void removeById(Long id) {
        log.info("JPA DAO: Attempting to remove SolarSystem with ID: " + id);
        SolarSystem entity = getById(id);
        if (entity == null) {
            log.error("JPA DAO: Failed to locate SolarSystem with ID: " + id + "; nothing to remove");
        } else {
            remove(entity);
        }
    }

    public List<SolarSystem> getAll() {
        try {
            log.info("JPA DAO: Searching for all SolarSystem");
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();

            Root<SolarSystem> r = cq.from(SolarSystem.class);
            cq.select(r);
            TypedQuery<SolarSystem> tq = em.createQuery(cq);
            List<SolarSystem> resultList = tq.getResultList();

            return (!resultList.isEmpty() ? resultList : null);
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    public void persist(SolarSystem entity) {
        log.info("JPA DAO: Attempting to persist SolarSystem: " + entity.toString());
        em.persist(entity);
    }

    public void merge(SolarSystem entity) {
        log.info("JPA DAO: Attempting to merge SolarSystem: " + entity.toString());
        em.merge(entity);
    }

    public void remove(SolarSystem entity) {
        log.info("JPA DAO: Attempting to remove SolarSystem: " + entity.toString());
        em.remove(entity);
    }

}

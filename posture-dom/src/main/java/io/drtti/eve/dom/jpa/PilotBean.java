package io.drtti.eve.dom.jpa;

import io.drtti.eve.dom.ccp.Pilot;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * @author cwinebrenner
 */
@Stateless
public class PilotBean {

    private final Logger log = Logger.getLogger(this.getClass());

    private final static String CCP_EVE_ID_CLASS_FIELD = "pilotId";

    @PersistenceContext
    EntityManager em;

    public Pilot getById(Long id) {
        log.info("JPA DAO: Searching for Pilot with ID: " + id);
        return em.find(Pilot.class, id);
    }

    public Pilot getByCcpEveId(Long ccpEveId) {
        log.info("JPA DAO: Searching for Pilot with CCP EVE ID: " + ccpEveId);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();

        Root<Pilot> r = cq.from(Pilot.class);
        cq.select(r).where(cb.equal(r.get(CCP_EVE_ID_CLASS_FIELD), ccpEveId));

        TypedQuery<Pilot> tq = em.createQuery(cq);
        return tq.getSingleResult();
    }

    public void removeById(Long id) {
        log.info("JPA DAO: Attempting to remove Pilot with ID: " + id);
        Pilot entity = getById(id);
        remove(entity);
    }

    public List<Pilot> getAll() {
        log.info("JPA DAO: Searching for all Pilot");
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();

        Root<Pilot> r = cq.from(Pilot.class);
        cq.select(r);
        TypedQuery<Pilot> tq = em.createQuery(cq);
        return tq.getResultList();
    }

    public void persist(Pilot entity) {
        log.info("JPA DAO: Attempting to persist Pilot: " + entity.toString());
        em.persist(entity);
    }

    public void merge(Pilot entity) {
        log.info("JPA DAO: Attempting to merge Pilot: " + entity.toString());
        em.merge(entity);
    }

    public void remove(Pilot entity) {
        log.info("JPA DAO: Attempting to remove Pilot: " + entity.toString());
        em.remove(entity);
    }

}

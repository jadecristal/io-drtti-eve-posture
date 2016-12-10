package io.drtti.eve.dom.jpa;

//import org.apache.log4j.Logger;

import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;

import java.io.Serializable;
import java.util.List;

/**
 * @author cwinebrenner
 * Adapted from baeldung.
 * http://www.baeldung.com/simplifying-the-data-access-layer-with-spring-and-java-generics
 *
 * See also: http://ctpconsulting.github.io/query/1.0.0.Alpha5/index.html
 * See also: https://code.google.com/archive/p/generic-dao/
 * Only here for io integration.
 */
public abstract class AbstractJpaDao<T extends Serializable> {

//    final Logger log = Logger.getLogger(this.getClass());

    @PersistenceContext
    EntityManager em;

    private Class<T> entityClass;

    void setEntityClass(Class<T> entityClass) {
//        log.info("JPA DAO: Configuring concrete implementation as " + entityClass.getName());
        this.entityClass = entityClass;
    }

    public T getById(Long id) {
//        log.info("JPA DAO: Searching for " + entityClass.getSimpleName() + " with ID " + id);
        return em.find(entityClass, id);
    }

    public void removeById(Long id) {
//        log.info("JPA DAO: Attempting to remove " + entityClass.getSimpleName() + " with ID " + id);
        T entity = getById(id);
        remove(entity);
    }

    public List<T> getAll() {
//        log.info("JPA DAO: Searching for all " + entityClass.getSimpleName());
        return em.createQuery("from " + entityClass.getName()).getResultList();
    }

    public void persist(T entity) {
//        log.info("JPA DAO: Attempting to persist " + entityClass.getSimpleName() + ": " + entity.toString());
        em.persist(entity);
    }

    public void merge(T entity) {
//        log.info("JPA DAO: Attempting to merge " + entityClass.getSimpleName() + ": " + entity.toString());
        em.merge(entity);
    }

    public void remove(T entity) {
//        log.info("JPA DAO: Attempting to remove " + entityClass.getSimpleName() + ": " + entity.toString());
        em.remove(entity);
    }

}

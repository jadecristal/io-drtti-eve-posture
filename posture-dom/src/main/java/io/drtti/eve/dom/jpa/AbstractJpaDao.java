package io.drtti.eve.dom.jpa;

import org.apache.log4j.Logger;

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

    private final Logger log = Logger.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager em;

    private Class<T> entityClass;

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T getById(Long id) {
        return em.find(entityClass, id);
    }

    public void removeById(Long id) {
        T entity = getById(id);
        remove(entity);
    }

    public List<T> getAll() {
        return em.createQuery("from " + entityClass.getName()).getResultList();
    }

    public void persist(T entity) {
        em.persist(entity);
    }

    public void merge(T entity) {
        em.merge(entity);
    }

    public void remove(T entity) {
        em.remove(entity);
    }

}

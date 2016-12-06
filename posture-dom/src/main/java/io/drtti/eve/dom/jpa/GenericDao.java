package io.drtti.eve.dom.jpa;

import java.io.Serializable;
import java.util.List;

/**
 * @author cwinebrenner
 */
public interface GenericDao<T extends Serializable> {

    public T getById(final Long id);
    public void removeById(final Long id);
    public List<T> getAll();
    public void persist(final T entity);
    public void merge(final T entity);
    public void remove(final T entity);

}

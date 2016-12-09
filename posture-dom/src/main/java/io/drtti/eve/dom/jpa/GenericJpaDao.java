package io.drtti.eve.dom.jpa;

import java.io.Serializable;

/**
 * @author cwinebrenner
 *
 * See also: http://stackoverflow.com/questions/3403909/get-generic-type-of-class-at-runtime
 */
public class GenericJpaDao<T extends Serializable> extends AbstractJpaDao<T> implements GenericDao<T> {

    public GenericJpaDao(Class<T> entityClass) {
        log.info(getClass().getGenericSuperclass().toString());
        //setEntityClass( (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0] );
        setEntityClass(entityClass);
    }

}

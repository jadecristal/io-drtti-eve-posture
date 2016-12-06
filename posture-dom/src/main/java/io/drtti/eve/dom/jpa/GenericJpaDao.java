package io.drtti.eve.dom.jpa;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * @author cwinebrenner
 *
 * See also: http://stackoverflow.com/questions/3403909/get-generic-type-of-class-at-runtime
 */
public class GenericJpaDao<T extends Serializable> extends AbstractJpaDao<T> implements GenericDao<T> {

    public GenericJpaDao() {
        setEntityClass( (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0] );
    }

}

package com.ls.persistent.mongodb.morphia;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * @param <T> the type of the entity
 * @param <K> the type of the key
 */
public class MorphiaDAO<T,K> extends BasicDAO<T,K> {

    public MorphiaDAO(Datastore ds) {
        super(ds);
    }
}

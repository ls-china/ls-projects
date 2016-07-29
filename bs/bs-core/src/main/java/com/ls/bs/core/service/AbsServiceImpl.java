package com.ls.bs.core.service;

import com.ls.bs.core.repository.IRespotry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hx on 2016/3/29.
 */
@SuppressWarnings({"unchecked", "unused"})
public abstract class AbsServiceImpl<T,ID extends Serializable> implements IService<T, ID> {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected IRespotry<T,ID> repository;

    public synchronized IRespotry<T, ID> getRepository() {
        if (null == repository) {
            repository = initRepository();
        }
        return repository;
    }
    protected abstract IRespotry<T,ID> initRepository();

    public List<T> findAll() {
        return getRepository().findAll();
    }

    public List<T> findAll(Sort sort) {
        return getRepository().findAll(sort);
    }

    public List<T> findAll(Iterable<ID> ids) {
        return getRepository().findAll(ids);
    }

    public T save(T s) {
        return getRepository().save(s);
    }

    public <S extends T> List<S> save(Iterable<S> entities) {
        return getRepository().save(entities);
    }

    public void flush() {
        getRepository().flush();
    }

    public <S extends T> S saveAndFlush(S entity) {
        return getRepository().saveAndFlush(entity);
    }

    public void deleteInBatch(Iterable<T> entities) {
        getRepository().deleteInBatch(entities);
    }

    public void delete(ID id) {
        getRepository().delete(id);
    }

    public void deleteAllInBatch() {
        getRepository().deleteAllInBatch();
    }

    public T getOne(ID id) {
        return getRepository().getOne(id);
    }

    public Page<T> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    public T findOne(Specification<T> spec) {
        return (T) getRepository().findOne(spec);
    }

    public List<T> findAll(Specification<T> spec) {
        return getRepository().findAll(spec);
    }

    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        return getRepository().findAll(spec, pageable);
    }

    public List<T> findAll(Specification<T> spec, Sort sort) {
        return getRepository().findAll(spec, sort);
    }

    long count(Specification<T> spec) {
        return getRepository().count(spec);
    }

}

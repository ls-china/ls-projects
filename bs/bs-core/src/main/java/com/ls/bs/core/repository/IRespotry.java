package com.ls.bs.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

/**
 * Created by hx on 2016/4/29.
 */
public interface IRespotry<T, ID extends Serializable> extends JpaRepository<T, ID > , JpaSpecificationExecutor {
}

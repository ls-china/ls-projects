package com.ls.user.domain;

import com.ls.bs.core.domain.IEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by hx on 2016/4/29.
 */
@Entity
public class User implements IEntity {

    @Id
    @GeneratedValue
    protected Long id;

}

package com.hsuforum.common.entity.impl;

import java.io.Serializable;

import com.hsuforum.common.entity.BaseEntity;

/**
 * Base Entity Implement
 * @author Marvin
 *
 */

public abstract class BaseEntityImpl<PK extends Serializable> implements BaseEntity<PK> {

    private static final long serialVersionUID = 6811222312202300235L;

}

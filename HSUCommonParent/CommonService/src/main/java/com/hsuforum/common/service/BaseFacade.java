package com.hsuforum.common.service;

import java.io.Serializable;

import com.hsuforum.common.entity.BaseEntity;

/**
 * Generic facade Interface
 *
 * @author Marvin
 *
 * @param <T>
 * @param <PK>
 */
@SuppressWarnings("rawtypes")
public interface BaseFacade<T extends BaseEntity, PK extends Serializable>{

}

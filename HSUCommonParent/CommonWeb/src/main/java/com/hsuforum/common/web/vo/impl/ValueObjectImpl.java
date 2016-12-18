package com.hsuforum.common.web.vo.impl;

import java.io.Serializable;

import com.hsuforum.common.entity.BaseEntity;
import com.hsuforum.common.web.vo.ValueObject;
/**
 * Value object implement
 * @author Marvin
 *
 * @param <T>
 * @param <PK>
 */
public class ValueObjectImpl<T extends BaseEntity<PK>, PK extends Serializable> implements ValueObject<T, PK>  {

	private static final long serialVersionUID = -1879239014908137738L;

	private T entity;

	public ValueObjectImpl(T entity) {
		super();
		this.setEntity(entity);
	}

	/**
	 * @see com.hsuforum.common.web.vo.ValueObject#getId()
	 */
	public PK getId() {
		return this.getEntity().getId();
		
	}
	
	/**
	 * @see com.hsuforum.common.web.vo.ValueObject#getEntity()
	 */
	public T getEntity() {
		return entity;
	}
	
	/**
	 * @see com.hsuforum.common.web.vo.ValueObject#setEntity(com.hsuforum.common.entity.BaseEntity)
	 */
	public void setEntity(final T entity) {
		this.entity = entity;
	}



}

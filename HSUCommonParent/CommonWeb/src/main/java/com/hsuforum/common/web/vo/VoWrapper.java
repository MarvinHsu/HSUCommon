package com.hsuforum.common.web.vo;

import java.io.Serializable;
import java.util.List;

import com.hsuforum.common.entity.BaseEntity;
/**
 * Value object wrapper Interface
 * @author Marvin
 *
 * @param <T>
 * @param <PK>
 */
public interface VoWrapper<T extends BaseEntity<PK>,PK extends Serializable> extends Serializable{
    
	/**
     * Translate entity to value object
     * 
     * @param entity
     * @return
     */
    public ValueObject<T, PK> wrap(T entity);
    /**
     * 
     * Translate list<T> to list<value objet>
     * @param list
     * @return
     */
    public List<ValueObject<T, PK>> wrap(List<T> list);
}

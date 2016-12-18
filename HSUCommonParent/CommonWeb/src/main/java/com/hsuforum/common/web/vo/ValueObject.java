package com.hsuforum.common.web.vo;

import java.io.Serializable;

import com.hsuforum.common.entity.BaseEntity;

/**
 * Value object interface
 * @author Marvin
 *
 * @param <T>
 * @param <PK>
 */
public interface ValueObject<T extends BaseEntity<PK>, PK extends Serializable> extends Serializable {
	
	/**
	 * Return entity.id
	 * @return
	 */
	PK getId();

	/**
	 * Get Entity
     * @return 
     */
    T getEntity();

   	/**
   	 * Set Entity
     * @param 
     */
    void setEntity(T bo); 
    
    
        

}

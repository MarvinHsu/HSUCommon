package com.hsuforum.common.entity;

import java.io.Serializable;

/**
 * Base Entity Interface
 *
 * @author Marvin
 *
 */
public interface BaseEntity<PK extends Serializable> extends Serializable {
    /**
     * Get Id of Entity
     *
     * @return PK
     */
	PK getId();

    /**
     * Set Id of Entity
     * @param id
     */
    void setId(PK id);
}

package com.hsuforum.common.entity.impl;

import java.util.Calendar;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.hsuforum.common.entity.SystemDateOperation;

/**
 * Get System Date Operation Entity Listener
 * 
 * @author Marvin
 *
 */
public class SystemDateEntityListener {

    /**
     * Before presist set create date
     * 
     */
    @PrePersist
    protected void toSetCreateDate(Object entity) {
        if (entity instanceof SystemDateOperation) {
            ((SystemDateOperation) entity).setCreateDate(Calendar.getInstance().getTime());
        }
    }

    /**
     * 
     * Before merge set update date
     */
    @PreUpdate
    protected void toSetUpdateDate(Object entity) {
        if (entity instanceof SystemDateOperation) {
            ((SystemDateOperation) entity).setUpdateDate(Calendar.getInstance().getTime());
        }
    }
}

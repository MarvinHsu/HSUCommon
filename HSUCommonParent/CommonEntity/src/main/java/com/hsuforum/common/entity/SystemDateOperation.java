package com.hsuforum.common.entity;

import java.util.Date;

/**
 * Get System Date Operation Interface
 * @author Marvin
 *
 */
public interface SystemDateOperation {

    /**
     * Set create entity system date
     * @param createDate
     */
    void setCreateDate(Date createDate);

    /**
     * Set update entity system date
     * @param updateDate
     */
    void setUpdateDate(Date updateDate);
}

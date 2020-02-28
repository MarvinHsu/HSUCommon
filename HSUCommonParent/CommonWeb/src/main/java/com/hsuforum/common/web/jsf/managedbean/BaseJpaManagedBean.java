package com.hsuforum.common.web.jsf.managedbean;

import java.io.Serializable;

import com.hsuforum.common.entity.BaseEntity;
import com.hsuforum.common.service.BaseJpaService;
import com.hsuforum.common.service.BaseService;

public interface BaseJpaManagedBean <T extends BaseEntity<ID>, ID extends Serializable, SERVICE extends BaseService<T, ID>, JPA_SERVICE extends BaseJpaService<T,ID>> extends Serializable{

	/**
	 * In create page, press cancel button, cancel create object and redirect read page
	 * @return 
	 */
	String doCancelCreateAction();

 
	/**
	 * In update page, press cancel button, cancel create object and redirect read page
	 * @return 
	 */
	String doCancelUpdateAction();

	/**
	 * In read page, press create button, init create object and redirect create page
	 * 
	 * @return 
	 */
	String doCreateAction();

	/**
	 * In update page, press delete button, delete object and redirect read page
	 * 
	 * @return 
	 */
	String doDeleteAction();
	
		
	/**
	 * Refresh read page, set dataModel and dataList null to make find data again 
	 * 
	 *
	 */
	void doRefreshData();

	/**
	 * In create page, press save button, create object and redirect read page
	 * 
	 * @return 
	 */
	String doSaveCreateAction();

	/**
	 * In update page, press save button, update object and redirect read page
	 * 
	 * @return 
	 */
	String doSaveUpdateAction();

	/**
	 * In read page, press update button, init updating object and redirect update page
	 * 
	 * @return 
	 */
	String doUpdateAction();
	
	/**
	 * In read page, press find button to find data
	 * @return
	 */
	String doFindAction() ;



	
}
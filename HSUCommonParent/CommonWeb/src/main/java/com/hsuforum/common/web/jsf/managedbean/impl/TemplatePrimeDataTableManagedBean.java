package com.hsuforum.common.web.jsf.managedbean.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.ListDataModel;

import com.hsuforum.common.entity.BaseEntity;
import com.hsuforum.common.service.BaseService;

/**
 * Prime faces template data table managed bean
 * @author Marvin
 *
 * @param <T>
 * @param <PK>
 * @param <SERVICE>
 */
public abstract class TemplatePrimeDataTableManagedBean<T extends BaseEntity<PK>, PK extends Serializable, SERVICE extends BaseService<T, PK>>
		extends BaseManagedBeanImpl<T, PK, SERVICE> {

	private static final long serialVersionUID = 6088970245648972762L;
	// data model of table
	private ListDataModel<T> dataModel;
	// data list data model
	private List<T> dataList;

	private boolean statusSearched = false;

	public TemplatePrimeDataTableManagedBean() {
		super();

	}

	/**
	 * Get data model
	 * @return
	 */
	public ListDataModel<T> getDataModel() {
		if (this.dataModel == null) {
			this.dataModel = new ListDataModel<T>();
			dataModel.setWrappedData(this.getDataList());
		}
		return this.dataModel;
	}

	/**
	 * Set data model
	 * 
	 * @param dataModel
	 *            
	 */
	public void setDataModel(ListDataModel<T> dataModel) {
		this.dataModel = dataModel;
	}

	/**
	 * Update entity 
	 */
	private void updateData() {
		if (this.getUpdatingData() != null) {

			this.getService().update(this.getUpdatingDataValue());
			
			this.setUpdatingData(null);

		}
	}

	/**
	 * Create entity
	 */
	private void createData() {
		try {
			this.getService().create(this.getUpdatingDataValue());
			
			this.setUpdatingData(null);

		} finally {
			this.doRefreshData();
		}
	}

	/**
	 * Delete entity
	 * 
	 * @param entity
	 *            
	 */
	private void deleteOneData(T entity) {
		try {
			if (entity != null) {
				this.getService().delete(entity);
			}
		} finally {
			this.doRefreshData();
		}
	}

	/**
	 * Set data model and data list null to refresh data
	 * 
	 * @see com.hsuforum.common.web.jsf.managedbean.impl.BaseManagedBeanImpl#doRefreshData()
	 */
	public void doRefreshData() {
		// set dataModel null
		this.setDataModel(null);
		// set dataList null
		this.setDataList(null);

	}

	
	/**
	 * @see com.hsuforum.common.web.jsf.managedbean.impl.BaseManagedBeanImpl#doCreateAction()
	 */
	public String doCreateAction() {

		this.initCreatingData();
		return "create";
	}

	

	
	/**
	 * @see com.hsuforum.common.web.jsf.managedbean.impl.BaseManagedBeanImpl#doSaveCreateAction()
	 */
	public String doSaveCreateAction() {

		
		this.setupUpdatingData();
		
		this.createData();
		
		this.doRefreshData();
		return "read";

	}


	/**
	 * @see com.hsuforum.common.web.jsf.managedbean.impl.BaseManagedBeanImpl#doCancelCreateAction()
	 */
	@Override
	public String doCancelCreateAction() {
		return doCancelAction();
	}


	/**
	 * @see com.hsuforum.common.web.jsf.managedbean.impl.BaseManagedBeanImpl#doUpdateAction()
	 */
	@Override
	public String doUpdateAction() {

		T entity = (T) this.getDataModel().getRowData();
		this.setUpdatingData(this.wrap(entity));

		this.initUpdatingData(this.getUpdatingData());

		return "update";

	}

	

	
	/**
	 * @see com.hsuforum.common.web.jsf.managedbean.impl.BaseManagedBeanImpl#doSaveUpdateAction()
	 */
	@Override
	public String doSaveUpdateAction() {

		
		this.setupUpdatingData();
		
		this.updateData();
		
		this.doRefreshData();
		return "read";

	}

	
	/**
	 * @see com.hsuforum.common.web.jsf.managedbean.impl.BaseManagedBeanImpl#doCancelUpdateAction()
	 */
	@Override
	public String doCancelUpdateAction() {
		return doCancelAction();

	}

	
	/**
	 * Cancel action
	 * @return
	 */
	private String doCancelAction() {

		this.setUpdatingData(null);
		this.doRefreshData();
		return "read";
	}

	
	/**
	 * @see com.hsuforum.common.web.jsf.managedbean.impl.BaseManagedBeanImpl#doDeleteAction()
	 */
	@Override
	public String doDeleteAction() {

		T entity = (T) this.getUpdatingDataValue();
		this.deleteOneData(entity);
		this.doRefreshData();

		return "read";

	}


	
	/**
	 * @see com.hsuforum.common.web.jsf.managedbean.impl.BaseManagedBeanImpl#doFindAction()
	 */
	@Override
	public String doFindAction() {
		this.setStatusSearched(true);
		this.setDataModel(null);
		return null;
	}

	
	/**
	 * Get data list if isStatusSearched() is true, then go findDataWithCriteria method
	 * else if isInitShowListData() is true, then go readAll method
	 * @return
	 */
	public List<T> getDataList() {

		if ((this.dataList == null)) {
			this.dataList = new ArrayList<T>();
		}

		if (this.isStatusSearched()) {
			this.dataList = (List<T>) this.findDataWithCriteria();
		} else if (this.isInitShowListData()) {
			this.dataList = (List<T>) this.findAllData();
		}

		return this.dataList;
	}

	/**
	 * Set data list
	 * 
	 * @param dataList
	 *            
	 */
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;

	}

	/**
	 * 
	 * Post construct to initial findCriteriaMap
	 * 
	 *
	 */
	protected abstract void initFindCriteriaMap();

	
	/**
	 * @see com.hsuforum.common.web.jsf.managedbean.impl.BaseManagedBeanImpl#findAllData()
	 */
	@Override
	protected List<T> findAllData() {
		return this.getService().findAll();
	}

	
	/**
	 * Is status of searched
	 * @return
	 */
	protected boolean isStatusSearched() {
		return statusSearched;
	}

	/**
	 * Set status of searched
	 * 
	 * @param statusSearched
	 *    
	 */
	protected void setStatusSearched(boolean statusSearched) {
		this.statusSearched = statusSearched;
	}
}

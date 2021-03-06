package com.hsuforum.common.web.jsf.managedbean.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hsuforum.common.entity.BaseEntity;
import com.hsuforum.common.service.BaseJpaService;
import com.hsuforum.common.service.BaseService;
import com.hsuforum.common.web.jsf.managedbean.BaseManagedBean;
import com.hsuforum.common.web.vo.ValueObject;
import com.hsuforum.common.web.vo.VoWrapper;
import com.hsuforum.common.web.vo.impl.ValueObjectImpl;
import com.hsuforum.common.web.vo.impl.VoWrapperImpl;

/**
 * Base JSF ManagedBean abstract implement
 * 
 * @author Marvin
 *
 * @param <T>       Entity
 * @param <SERVICE> Service
 */
public abstract class BaseManagedBeanImpl<T extends BaseEntity<ID>, ID extends Serializable, SERVICE extends BaseService<T, ID>, JPA_SERVICE extends BaseJpaService<T, ID>>
		implements BaseManagedBean<T, ID, SERVICE, JPA_SERVICE> {

	private static final long serialVersionUID = -8002301686636248272L;

	// value object of managedBean for create and update action
	private ValueObject<T, ID> updatingData;

	// main service of managedBean
	private SERVICE service;

	// main jpa service of managedBean
	private JPA_SERVICE jpaService;

	// find criteria map
	private Map<String, ? extends Object> findCriteriaMap;
	// find operation map
	private Map<String, String> findOperMap;
	// find sort map
	private Map<String, String> findSortMap;
	// is initial show search of data
	private boolean initShowListData = true;

	private VoWrapper<T, ID> voWrapper = new VoWrapperImpl<T, ID>();

	public BaseManagedBeanImpl() {
		super();
	}

	/**
	 * default search method
	 * 
	 * @return List<?>
	 */
	protected abstract List<T> findAllData();

	/**
	 * search method, will use findCriteria* method of service layer to search
	 *
	 * @return
	 */
	protected List<T> findDataWithCriteria() {
		List<T> list = null;
		if (this.getFindCriteriaMap().size() == 0) {
			list = this.findAllData();
		} else {

			if (this.getFindOperMap() != null && this.getFindOperMap().size() > 0 && this.getFindSortMap() != null
					&& this.getFindSortMap().size() > 0) {
				list = this.getService().findByCriteriaMap(this.getFindCriteriaMap(), this.getFindOperMap(),
						this.getFindSortMap());
			} else if (this.getFindSortMap() != null && this.getFindSortMap().size() > 0) {
				list = this.getService().findByCriteriaMap(this.getFindCriteriaMap(), this.getFindSortMap());
			} else if (this.getFindOperMap() != null && this.getFindOperMap().size() > 0) {
				list = this.getService().findByCriteriaMapWithOper(this.getFindCriteriaMap(), this.getFindOperMap());
			} else {
				list = this.getService().findByCriteriaMap(this.getFindCriteriaMap(), null, null);
			}

		}
		return list;
	}

	/**
	 * initialize creating data
	 */
	protected abstract void initCreatingData();

	/**
	 * initialize updating data
	 *
	 * @param updatingData
	 */
	protected abstract void initUpdatingData(ValueObject<T, ID> updatingData);

	/**
	 * setup relation entity of updating data
	 */
	protected abstract void setupUpdatingData();

	/**
	 * Get entity of updating data
	 * 
	 * @return entity
	 */
	protected T getUpdatingDataValue() {
		if (this.updatingData != null) {
			return this.updatingData.getEntity();
		}
		return null;
	}

	/**
	 * @see com.hsuforum.common.web.jsf.managedbean.BaseManagedBean#doRefreshData()
	 */
	@Override
	public abstract void doRefreshData();

	/**
	 * @see com.hsuforum.common.web.jsf.managedbean.BaseManagedBean#doCreateAction()
	 */
	@Override
	public abstract String doCreateAction();

	/**
	 * @see com.hsuforum.common.web.jsf.managedbean.BaseManagedBean#doSaveCreateAction()
	 */
	@Override
	public abstract String doSaveCreateAction();

	/**
	 * @see com.hsuforum.common.web.jsf.managedbean.BaseManagedBean#doCancelCreateAction()
	 */
	@Override
	public abstract String doCancelCreateAction();

	/**
	 * @see com.hsuforum.common.web.jsf.managedbean.BaseManagedBean#doUpdateAction()
	 */
	@Override
	public abstract String doUpdateAction(T entity);

	/**
	 * @see com.hsuforum.common.web.jsf.managedbean.BaseManagedBean#doSaveUpdateAction()
	 */
	@Override
	public abstract String doSaveUpdateAction();

	/**
	 * @see com.hsuforum.common.web.jsf.managedbean.BaseManagedBean#doCancelUpdateAction()
	 */
	@Override
	public abstract String doCancelUpdateAction();

	/**
	 * @see com.hsuforum.common.web.jsf.managedbean.BaseManagedBean#doDeleteAction()
	 */
	@Override
	public abstract String doDeleteAction();

	/**
	 * @see com.hsuforum.common.web.jsf.managedbean.BaseManagedBean#doFindAction()
	 */
	@Override
	public abstract String doFindAction();

	/**
	 * Get updating data
	 * 
	 * @return
	 */
	public ValueObject<T, ID> getUpdatingData() {
		return (ValueObjectImpl<T, ID>) this.updatingData;
	}

	/**
	 * Set updating data
	 * 
	 * @param updatingData
	 */
	public void setUpdatingData(ValueObject<T, ID> updatingData) {
		this.updatingData = updatingData;
	}

	/**
	 * Get service
	 */
	public SERVICE getService() {

		return this.service;
	}

	/**
	 * Set service
	 * 
	 * @param service
	 */
	public void setService(SERVICE service) {
		this.service = service;
	}

	/**
	 * Get jpa service
	 * 
	 * @return
	 */
	public JPA_SERVICE getJpaService() {
		return jpaService;
	}

	/**
	 * Set jpa service
	 * 
	 * @param jpaService
	 */
	public void setJpaService(JPA_SERVICE jpaService) {
		this.jpaService = jpaService;
	}

	/**
	 * Get value object wrapper
	 * 
	 * @return VoWrapper<T, ID>
	 */
	public VoWrapper<T, ID> getVoWrapper() {
		return voWrapper;
	}

	/**
	 * set value object wrapper
	 * 
	 * @param voWrapper
	 */
	public void setVoWrapper(VoWrapper<T, ID> voWrapper) {
		this.voWrapper = voWrapper;
	}

	/**
	 * Wrap entity to value object
	 * 
	 * @param bo Business Object
	 * @return ValueObject<T, ID>
	 */
	public ValueObject<T, ID> wrap(T entity) {
		return (ValueObject<T, ID>) this.getVoWrapper().wrap(entity);
	}

	/**
	 * Is showing data in initial
	 * 
	 * @return boolean
	 */
	protected boolean isInitShowListData() {
		return initShowListData;
	}

	/**
	 * Set showing data in initial
	 * 
	 * @param initShowListData
	 */
	protected void setInitShowListData(boolean initShowListData) {
		this.initShowListData = initShowListData;
	}

	/**
	 * Get find operation map
	 * 
	 * @return
	 */
	public Map<String, String> getFindOperMap() {
		return findOperMap;
	}

	/**
	 * set find operation map
	 * 
	 * @param findOperMap
	 * 
	 */
	public void setFindOperMap(Map<String, String> findOperMap) {
		this.findOperMap = findOperMap;
	}

	/**
	 * Get find criteria map
	 * 
	 * @return
	 */
	public Map<String, ? extends Object> getFindCriteriaMap() {
		return findCriteriaMap;
	}

	/**
	 * Set find criteria map
	 * 
	 * @param findCriteriaMap
	 */
	public void setFindCriteriaMap(Map<String, ? extends Object> findCriteriaMap) {
		this.findCriteriaMap = findCriteriaMap;
	}

	/**
	 * Get find sorting map
	 * 
	 * @return 查詢排序Map
	 */
	public Map<String, String> getFindSortMap() {
		return findSortMap;
	}

	/**
	 * Set find sorting map
	 * 
	 * @param findSortMap
	 */
	public void setFindSortMap(Map<String, String> findSortMap) {
		this.findSortMap = findSortMap;
	}

}

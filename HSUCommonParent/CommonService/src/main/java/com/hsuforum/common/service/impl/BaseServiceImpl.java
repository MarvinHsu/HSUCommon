package com.hsuforum.common.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hsuforum.common.dao.BaseDao;
import com.hsuforum.common.entity.BaseEntity;
import com.hsuforum.common.service.BaseService;

/**
 * Base service implement
 *
 * @author
 *
 * @param <T>
 * @param <PK>
 * @param <DAO>
 */
public abstract class BaseServiceImpl<T extends BaseEntity<PK>, PK extends Serializable, DAO extends BaseDao<T, PK>>
		implements BaseService<T, PK> {

	private static Log logger = LogFactory.getLog(BaseServiceImpl.class);

	// Data access object of service，Spring will inject it
	private DAO dao;

	/**
	 * @see com.hsuforum.common.service.BaseService#count()
	 */
	@Override
	public int count() {

		return this.getDao().count();

	}

	/**
	 * @see com.hsuforum.common.service.BaseService#create(java.lang.Object)
	 */
	@Override
	public T create(T entity) {

		return (T) this.getDao().create(entity);
	}

	/**
	 * @see com.hsuforum.common.service.BaseService#delete(java.util.List)
	 */
	@Override
	public void delete(List<T> objList) {
		for (T obj : objList) {
			this.delete(obj);
		}
	}

	/**
	 * @see com.hsuforum.common.service.BaseService#delete(java.lang.Object)
	 */
	@Override
	public void delete(T entity) {

		this.getDao().delete(entity);
	}

	/**
	 * @see com.hsuforum.common.service.BaseService#find(java.lang.String,
	 *      boolean)
	 */
	@Override
	public List<T> find(String sortName, boolean isDesc) {
		return this.getDao().find(sortName, isDesc);
	}

	/**
	 * @see com.hsuforum.common.service.BaseService#find(java.lang.String,
	 *      boolean, int, int)
	 */
	@Override
	public List<T> find(String sortName, boolean isDesc, int from, int to) {
		return this.getDao().find(sortName, isDesc, from, to);
	}

	/**
	 * @see com.hsuforum.common.service.BaseService#find(java.lang.String,
	 *      boolean, java.util.Map)
	 */
	@Override
	public List<T> find(String sortName, boolean isDesc, Map<String, ? extends Object> paramMap) {
		return this.getDao().find(sortName, isDesc, paramMap);
	}

	/**
	 * @see com.hsuforum.common.service.BaseService#findAll()
	 */
	@Override
	public List<T> findAll() {

		return this.getDao().findAll();

	}

	/**
	 * @see com.hsuforum.common.service.BaseService#findAll(java.lang.String,
	 *      boolean)
	 */
	@Override
	public List<T> findAll(String sortName, boolean isDesc) {

		return this.getDao().findAll(sortName, isDesc);
	}

	/**
	 * @see com.hsuforum.common.service.BaseService#findByCriteriaMap(java.util.Map)
	 */
	@Override
	public List<T> findByCriteriaMap(Map<String, ? extends Object> criteriaMap) {
		return this.getDao().findByCriteriaMap(criteriaMap);
	}

	/**
	 * @see com.hsuforum.common.service.BaseService#findByCriteriaMap(java.util.Map,
	 *      java.util.Map)
	 */
	@Override
	public List<T> findByCriteriaMap(Map<String, ? extends Object> criteriaMap, Map<String, String> sortMap) {
		return this.getDao().findByCriteriaMap(criteriaMap, sortMap);
	}

	/**
	 * @see com.hsuforum.common.service.BaseService#findByCriteriaMap(java.util.Map,
	 *      java.util.Map, java.util.Map)
	 */
	@Override
	public List<T> findByCriteriaMap(Map<String, ? extends Object> criteriaMap, Map<String, String> operMap,
			Map<String, String> sortMap) {
		return this.getDao().findByCriteriaMap(criteriaMap, operMap, sortMap);
	}

	/**
	 * @see com.hsuforum.common.service.BaseService#findByCriteriaMapReturnUnique(java.util.Map)
	 */
	@Override
	public T findByCriteriaMapReturnUnique(Map<String, ? extends Object> criteriaMap) {
		return this.getDao().findByCriteriaMapReturnUnique(criteriaMap);
	}

	/**
	 * @see com.hsuforum.common.service.BaseService#findByCriteriaMapReturnUnique(java.util.Map,
	 *      java.util.Map)
	 */
	@Override
	public T findByCriteriaMapReturnUnique(Map<String, ? extends Object> criteriaMap, Map<String, String> sortMap) {
		return this.getDao().findByCriteriaMapReturnUnique(criteriaMap, sortMap);
	}

	/**
	 * @see com.hsuforum.common.service.BaseService#findByCriteriaMapReturnUnique(java.util.Map,
	 *      java.util.Map, java.util.Map)
	 */
	@Override
	public T findByCriteriaMapReturnUnique(Map<String, ? extends Object> criteriaMap, Map<String, String> operMap,
			Map<String, String> sortMap) {
		return this.getDao().findByCriteriaMapReturnUnique(criteriaMap, operMap, sortMap);
	}

	/**
	 * @see com.hsuforum.common.service.BaseService#findByCriteriaMapWithOper(java.util.Map,
	 *      java.util.Map)
	 */
	@Override
	public List<T> findByCriteriaMapWithOper(Map<String, ? extends Object> criteriaMap, Map<String, String> operMap) {
		return this.getDao().findByCriteriaMapWithOper(criteriaMap, operMap);
	}

	@Override
	public T findByCriteriaMapWithOperReturnUnique(Map<String, ? extends Object> criteriaMap,
			Map<String, String> operMap) {
		return this.getDao().findByCriteriaMapWithOperReturnUnique(criteriaMap, operMap);
	}

	/**
	 * @see com.hsuforum.common.service.BaseService#findByPK(java.io.Serializable)
	 */
	@Override
	public T findByPK(PK pk) {

		return (T) this.getDao().findByPK(pk);

	}

	/**
	 * @see com.hsuforum.common.service.BaseService#findByProperty(java.lang.String,
	 *      java.lang.Object)
	 */
	@Override
	public List<T> findByProperty(String propertyName, Object propertyValue) {
		return this.getDao().findByProperty(propertyName, propertyValue);

	}

	/**
	 * @see com.hsuforum.common.service.BaseService#findByProperty(java.lang.String,
	 *      java.lang.Object, java.lang.String, boolean)
	 */
	@Override
	public List<T> findByProperty(String propertyName, Object propertyValue, String sortName, boolean isDesc) {
		return this.getDao().findByProperty(propertyName, propertyValue, sortName, isDesc);
	}

	/**
	 * Get data access object
	 *
	 * @return DAO
	 */

	public DAO getDao() {
		return this.dao;
	}

	/**
	 * Set data access object
	 *
	 * @param dao
	 */
	public void setDao(DAO dao) {
		this.dao = dao;
	}

	/**
	 * @see com.hsuforum.common.service.BaseService#refresh(java.lang.Object)
	 */
	@Override
	public void refresh(Object obj) {
		this.getDao().refresh(obj);
	}

	/**
	 * @see com.hsuforum.common.service.BaseService#update(java.lang.Object)
	 */
	@Override
	public T update(T entity) {

		return this.getDao().update(entity);
	}
}

package com.hsuforum.common.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Base service interface
 *
 * @author Marvin
 *
 * @param <T>
 * @param <PK>
 */
public interface BaseService<T, PK extends Serializable> extends Serializable {

	/**
	 * count entity
	 *
	 * @return int
	 */
	int count();
	/**
	 * Find data
	 * 
	 * @param sortName
	 * @param isDesc
	 * @return
	 */
	List<T> find(String sortName, boolean isDesc);

	/**
	 * Find data between from to end
	 * 
	 * @param sortName
	 * @param isDesc
	 * @param from
	 * @param end
	 * @return
	 */
	List<T> find(String sortName, boolean isDesc, int from, int end);

	/**
	 * Find data
	 * 
	 * @param sortName
	 * @param isDesc
	 * @param paramMap
	 * @return
	 */
	List<T> find(String sortName, boolean isDesc, Map<String, ? extends Object> paramMap);

	/**
	 * Find all data
	 *
	 * @return List<T>
	 */
	List<T> findAll();

	/**
	 * Find all data
	 * 
	 * @param sortName
	 * @param isDesc
	 * @return
	 */
	List<T> findAll(String sortName, boolean isDesc);

	/**
	 * Find data by criteria map
	 *
	 * @param criteriaMap
	 *            <String, ? extends Object>
	 * @return List<T>
	 */
	List<T> findByCriteriaMap(Map<String, ? extends Object> criteriaMap);

	/**
	 * Find data by criteria map
	 *
	 * @param criteriaMap
	 *            <String, ? extends Object>
	 * @param sortMap
	 *            <String, String>
	 * @return List<T>
	 */
	List<T> findByCriteriaMap(Map<String, ? extends Object> criteriaMap, Map<String, String> sortMap);

	/**
	 * Find data by criteria map
	 * 
	 * @param criteriaMap
	 *            <String, ? extends Object>
	 * @param operMap
	 *            <String, String>
	 * @param sortMap
	 *            <String, String>
	 * @return
	 */
	List<T> findByCriteriaMap(Map<String, ? extends Object> criteriaMap, Map<String, String> operMap,
			Map<String, String> sortMap);

	/**
	 *
	 * Find data by criteria map, return unique entity
	 *
	 * @param criteriaMap
	 *            <String, ? extends Object>
	 * @return T
	 */
	T findByCriteriaMapReturnUnique(Map<String, ? extends Object> criteriaMap);

	/**
	 *
	 * Find data by criteria map, return unique entity
	 *
	 * @param criteriaMap
	 *            <String, ? extends Object>
	 * @param sortMap
	 *            <String, String>
	 * @return T
	 */
	T findByCriteriaMapReturnUnique(Map<String, ? extends Object> criteriaMap, Map<String, String> sortMap);

	/**
	 *
	 * Find data by criteria map, return unique entity
	 *
	 * @param criteriaMap
	 *            <String, ? extends Object>
	 * @param operMap
	 *            <String, String>
	 * @param sortMap
	 *            <String, String>
	 * @return T
	 */
	T findByCriteriaMapReturnUnique(Map<String, ? extends Object> criteriaMap, Map<String, String> operMap,
			Map<String, String> sortMap);

	/**
	 * Find data by criteria map
	 * 
	 * @param criteriaMap
	 *            <String, ? extends Object>
	 * @param operMap
	 *            <String, String>
	 * @return
	 */
	List<T> findByCriteriaMapWithOper(Map<String, ? extends Object> criteriaMap, Map<String, String> operMap);

	/**
	 * Find data by criteria map
	 * 
	 * @param criteriaMap
	 * @param operMap
	 * @param sortMap
	 * @return
	 */
	T findByCriteriaMapWithOperReturnUnique(Map<String, ? extends Object> criteriaMap, Map<String, String> operMap);

	/**
	 * Find by primary key
	 *
	 * @param pk
	 */
	T findByPK(PK pk);

	/**
	 * Find data by property name, property value
	 *
	 * @param propertyName
	 * 
	 * @param propertyValue
	 * 
	 * @return List<T>
	 */
	List<T> findByProperty(String propertyName, Object propertyValue);

	/**
	 * Find data by property name, property value
	 *
	 * @param propertyName
	 * 
	 * @param propertyValue
	 * 
	 * @param sortName
	 * 
	 * @param isDesc
	 * 
	 * @return List<T>
	 */
	List<T> findByProperty(String propertyName, Object propertyValue, String sortName, boolean isDesc);

	/**
	 * Refresh entity
	 * 
	 * @param obj
	 */
	void refresh(Object obj);


}

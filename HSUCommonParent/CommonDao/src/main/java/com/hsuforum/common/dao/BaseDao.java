package com.hsuforum.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import jakarta.persistence.EntityManager;



/**
 * Data access object interface
 *
 * @author Marvin
 *
 * @param <T>
 * @param <PK>
 */
public interface BaseDao<T, PK extends Serializable> extends Serializable {

	/**
	 * count entity
	 *
	 * @return
	 */
	int count();

	/**
	 * count entity
	 *
	 * @return
	 */
	int count(StringBuffer queryString);

	/**
	 * count entity
	 * 
	 * @param queryString
	 * @param paramMap
	 * @return
	 */
	int count(StringBuffer queryString, Map<String, ? extends Object> paramMap);
	
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
	 * Find data between from to end
	 * 
	 * @param sortName
	 * @param isDesc
	 * @param paramMap
	 * @param from
	 * @param end
	 * @return
	 */
	List<T> find(String sortName, boolean isDesc, Map<String, ? extends Object> paramMap, int from, int end);

	/**
	 * Find data
	 * 
	 * @param queryString
	 * @return
	 */
	List<T> find(StringBuffer queryString);

	/**
	 * Find data between from to end
	 * 
	 * @param queryString
	 * @param from
	 * @param end
	 * @return
	 */
	List<T> find(StringBuffer queryString, int from, int to);

	/**
	 *
	 * Find data between from to end
	 * 
	 * @param queryString
	 * @param paramMap
	 * @param from
	 * @param end
	 * @return
	 */
	List<T> find(StringBuffer queryString, Map<String, ? extends Object> paramMap, int from, int end);

	/**
	 * Find data
	 * 
	 * @param queryString
	 * @param sortNameMap
	 *            sort name map (String,Boolean), Boolean is desc
	 * @param paramMap
	 * @return
	 */
	List<T> find(StringBuffer queryString, Map<String, Boolean> sortNameMap, Map<String, ? extends Object> paramMap);

	/**
	 * 
	 * Find data between from to end
	 * 
	 * @param queryString
	 * @param sortNameMap
	 *            sort name map (String,Boolean), Boolean is desc
	 * @param paramMap
	 * @param from
	 * @param end
	 * @return
	 */
	List<T> find(StringBuffer queryString, Map<String, Boolean> sortNameMap, Map<String, ? extends Object> paramMap,
			int from, int end);

	/**
	 *
	 * Find data
	 * 
	 * @param queryString
	 * @param sortName
	 * @param isDesc
	 * @param paramMap
	 * @return
	 */
	List<T> find(StringBuffer queryString, String sortName, boolean isDesc, Map<String, ? extends Object> paramMap);

	/**
	 *
	 * Find data between from to end
	 * 
	 * @param queryString
	 * @param sortName
	 * @param isDesc
	 * @param paramMap
	 * @param from
	 * @param end
	 * @return
	 */
	List<T> find(StringBuffer queryString, String sortName, boolean isDesc, Map<String, ? extends Object> paramMap,
			int from, int end);

	/**
	 * Find all data
	 * 
	 * @return
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
	 * @return List<T>
	 */
	List<T> findByCriteriaMap(Map<String, ? extends Object> criteriaMap, Map<String, String> operMap,
			Map<String, String> sortMap);

	/**
	 *
	 * Find data by criteria map, return unique entity
	 *
	 * @param criteriaMap<String,
	 *            ? extends Object>
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
	 * @return List<T>
	 */
	List<T> findByCriteriaMapWithOper(Map<String, ? extends Object> criteriaMap, Map<String, String> operMap);

	/**
	 * Find data by criteria map
	 * 
	 * @param criteriaMap
	 * @param operMap
	 * @return T
	 */
	T findByCriteriaMapWithOperReturnUnique(Map<String, ? extends Object> criteriaMap, Map<String, String> operMap);

	/**
	 * Find data by query String, param map
	 * 
	 * @param queryString
	 * @param paramMap
	 * @return List<T>
	 */
	List<T> findByNamedParams(StringBuffer queryString, Map<String, ? extends Object> paramMap);

	/**
	 * Find data by query String, param map and return unique entity
	 * 
	 * @param queryString
	 * @param paramMap
	 * @return
	 */
	T findByNamedParamsReturnUnique(StringBuffer queryString, Map<String, ? extends Object> paramMap);

	/**
	 * Find data by queryName
	 * 
	 * @param queryName
	 *            JPQL query name
	 * @return List<T>
	 */
	List<T> findByNamedQuery(StringBuffer queryName);

	/**
	 * Find data by queryName、param map
	 * 
	 * @param queryName
	 *            JPQL query name
	 * @param paramMap
	 * @return List<T>
	 */
	List<T> findByNamedQuery(StringBuffer queryName, Map<String, ? extends Object> paramMap);

	/**
	 * Find by primary key
	 *
	 * @param pk
	 * 
	 * @return T
	 */
	T findByPK(PK pk);

	/**
	 * Find data by property name, property value
	 *
	 * @param propertyName
	 * 
	 * @param propertyValue
	 * 
	 * @return List<T> 查詢出的List物件
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
	 * Get EntityManager
	 */
	EntityManager getEntityManager();

	/**
	 * Set EntityManager
	 * 
	 * @param entityManager
	 */
	void setEntityManager(EntityManager entityManager);

	/**
	 * According primary key detect entity exist or not
	 *
	 * @param pk
	 * 
	 * @return boolean
	 */
	boolean isExist(PK pk);

	/**
	 * Refresh entity
	 * 
	 * @param obj
	 */
	void refresh(Object entity);



}

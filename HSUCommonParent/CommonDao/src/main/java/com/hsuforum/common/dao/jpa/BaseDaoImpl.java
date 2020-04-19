package com.hsuforum.common.dao.jpa;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;

import com.hsuforum.common.dao.BaseDao;
import com.hsuforum.common.entity.BaseEntity;

/**
 * Data access object implement
 *
 * @author Mavin
 * @param <T>
 * @param <PK>
 */
public class BaseDaoImpl<T extends BaseEntity<PK>, PK extends Serializable> implements BaseDao<T, PK> {

	private static final long serialVersionUID = 7083892661789424395L;

	private static Log logger = LogFactory.getLog(BaseDaoImpl.class);

	@PersistenceContext(name = "persistenceUnit")
	private EntityManager entityManager;

	transient private Class<T> persistClass;

	/**
	 * Constructor
	 * 
	 */
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		Type genericSuperClass = getClass().getGenericSuperclass();

		ParameterizedType parametrizedType = null;
		while (parametrizedType == null) {
		   if ((genericSuperClass instanceof ParameterizedType)) {
		       parametrizedType = (ParameterizedType) genericSuperClass;
		   } else {
		       genericSuperClass = ((Class<?>) genericSuperClass).getGenericSuperclass();
		   }
		}

		this.persistClass = (Class<T>) parametrizedType.getActualTypeArguments()[0];
		
	}

	/**
	 * Constructor, will configure persist class
	 *
	 * @param persistentClass
	 */
	public BaseDaoImpl(Class<T> persistentClass) {
		super();
		this.persistClass = persistentClass;
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#count()
	 */
	@Override
	public int count() {
		@SuppressWarnings("rawtypes")
		Iterator iter = this.getEntityManager()
				.createQuery("SELECT COUNT(entity) FROM " + getPersistClass().getSimpleName() + " entity ")
				.getResultList().iterator();

		int result = 0;
		if (iter.hasNext()) {
			result = ((Number) iter.next()).intValue();
		}
		return result;
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#count(java.lang.StringBuffer)
	 */
	@Override
	public int count(StringBuffer queryString) {
		@SuppressWarnings("rawtypes")
		Iterator iter = this.getEntityManager().createQuery(queryString.toString()).getResultList().iterator();

		int result = 0;
		if (iter.hasNext()) {
			result = ((Number) iter.next()).intValue();
		}
		return result;
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#count(java.lang.StringBuffer,
	 *      java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public int count(StringBuffer queryString, Map<String, ? extends Object> paramMap) {

		Query query = this.getEntityManager().createQuery(queryString.toString());
		if (paramMap != null) {
			for (String param : paramMap.keySet()) {
				query.setParameter(param, paramMap.get(param));

			}
		}

		Iterator iter = query.getResultList().iterator();

		int result = 0;
		if (iter.hasNext()) {
			result = ((Number) iter.next()).intValue();
		}
		return result;
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#create(java.lang.Object)
	 */
	@Override
	public T create(T entity) {

		this.getEntityManager().persist(entity);
		this.getEntityManager().flush();
		return entity;
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#delete(java.lang.Object)
	 */
	@Override
	public void delete(T entity) {

		entity = this.getEntityManager().find(this.getPersistClass(), entity.getId());

		this.getEntityManager().remove(entity);
		// flush使資料保持一致
		this.getEntityManager().flush();

	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#excute(java.lang.StringBuffer)
	 */
	@Override
	public int excute(StringBuffer queryString) {

		return this.excute(queryString, null);

	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#excute(java.lang.StringBuffer,
	 *      java.util.Map)
	 */
	@Override
	public int excute(StringBuffer queryString, Map<String, ? extends Object> paramMap) {
		Query query = this.getEntityManager().createQuery(queryString.toString());
		if (paramMap != null) {
			for (String param : paramMap.keySet()) {
				query.setParameter(param, paramMap.get(param));

			}
		}
		return query.executeUpdate();

	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#excuteNativeSQL(java.lang.StringBuffer)
	 */
	@Override
	public int excuteNativeSQL(StringBuffer queryString) {
		return this.excuteNativeSQL(queryString, null);
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#excuteNativeSQL(java.lang.StringBuffer,
	 *      java.util.Map)
	 */
	@Override
	public int excuteNativeSQL(StringBuffer queryString, Map<String, ? extends Object> paramMap) {
		Query query = this.getEntityManager().createNativeQuery(queryString.toString());
		if (paramMap != null) {
			for (String param : paramMap.keySet()) {
				query.setParameter(param, paramMap.get(param));

			}
		}
		return query.executeUpdate();

	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#find(java.lang.String, boolean)
	 */
	@Override
	public List<T> find(String sortName, boolean isDesc) {
		List<T> list = null;

		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT DISTINCT entity FROM " + this.getPersistClass().getSimpleName()).append(" entity ");
		queryString.append("ORDER BY entity." + sortName + " ");
		if (isDesc) {
			queryString.append("DESC  ");
		}
		// 讀取出所有的資料
		list = this.find(queryString);

		return list;
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#find(java.lang.String, boolean, int,
	 *      int)
	 */
	@Override
	public List<T> find(String sortName, boolean isDesc, int from, int end) {
		List<T> list = null;

		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT DISTINCT entity FROM " + this.getPersistClass().getSimpleName()).append(" entity ");
		queryString.append("ORDER BY entity." + sortName + " ");
		if (isDesc) {
			queryString.append("DESC  ");
		}

		list = this.find(queryString, from, end);

		return list;
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#find(java.lang.String, boolean,
	 *      java.util.Map)
	 */
	@Override
	public List<T> find(String sortName, boolean isDesc, Map<String, ? extends Object> paramMap) {
		List<T> list = null;

		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT DISTINCT entity FROM " + this.getPersistClass().getSimpleName()).append(" entity ");
		queryString.append("ORDER BY entity." + sortName + " ");
		if (isDesc) {
			queryString.append("DESC  ");
		}

		list = this.findByNamedParams(queryString, paramMap);

		return list;
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#find(java.lang.String, boolean,
	 *      java.util.Map, int, int)
	 */
	@Override
	public List<T> find(String sortName, boolean isDesc, Map<String, ? extends Object> paramMap, int from, int end) {
		List<T> list = null;

		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT DISTINCT entity FROM " + this.getPersistClass().getSimpleName()).append(" entity ");
		queryString.append("ORDER BY entity." + sortName + " ");
		if (isDesc) {
			queryString.append("DESC  ");
		}

		list = this.find(queryString, paramMap, from, end);

		return list;
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#find(java.lang.StringBuffer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(StringBuffer queryString) {
		return this.getEntityManager().createQuery(queryString.toString()).getResultList();
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#find(java.lang.StringBuffer, int,
	 *      int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(StringBuffer queryString, int from, int end) {

		int maxResult = end - from;
		Query query = this.getEntityManager().createQuery(queryString.toString());

		query.setFirstResult(from);
		query.setMaxResults(maxResult);
		return query.getResultList();

	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#find(java.lang.StringBuffer,
	 *      java.util.Map, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(StringBuffer queryString, Map<String, ? extends Object> paramMap, int from, int end) {
		int maxResult = end - from;
		Query query = this.getEntityManager().createQuery(queryString.toString());
		if (paramMap != null) {
			for (String param : paramMap.keySet()) {
				query.setParameter(param, paramMap.get(param));

			}
		}
		query.setFirstResult(from);
		query.setMaxResults(maxResult);
		return query.getResultList();

	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#find(java.lang.StringBuffer,
	 *      java.util.Map, java.util.Map)
	 */
	@Override
	public List<T> find(StringBuffer queryString, Map<String, Boolean> sortNameMap,
			Map<String, ? extends Object> paramMap) {
		List<T> list = null;
		try {
			int i = 0;
			for (String key : sortNameMap.keySet()) {
				if (i == 0) {
					queryString.append("ORDER BY ");
				}
				queryString.append("entity." + key);
				if (sortNameMap.get(key)) {
					queryString.append(" DESC  ");
				}
				if (i != (sortNameMap.size() - 1)) {
					queryString.append(", ");
				}
				i++;
			}

			list = this.findByNamedParams(queryString, paramMap);
		} catch (DataAccessException dataAccessException) {

			throw dataAccessException;
		}
		return list;
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#find(java.lang.StringBuffer,
	 *      java.util.Map, java.util.Map, int, int)
	 */
	@Override
	public List<T> find(StringBuffer queryString, Map<String, Boolean> sortNameMap,
			Map<String, ? extends Object> paramMap, int from, int end) {
		List<T> list = null;
		try {
			int i = 0;
			for (String key : sortNameMap.keySet()) {
				if (i == 0) {
					queryString.append("ORDER BY ");
				}
				queryString.append("entity." + key);
				if (sortNameMap.get(key)) {
					queryString.append(" DESC  ");
				}
				if (i != (sortNameMap.size() - 1)) {
					queryString.append(", ");
				}
				i++;
			}

			list = this.find(queryString, paramMap, from, end);
		} catch (DataAccessException dataAccessException) {

			throw dataAccessException;
		}
		return list;
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#find(java.lang.StringBuffer,
	 *      java.lang.String, boolean, java.util.Map)
	 */
	@Override
	public List<T> find(StringBuffer queryString, String sortName, boolean isDesc,
			Map<String, ? extends Object> paramMap) {
		List<T> list = null;
		try {

			queryString.append("ORDER BY entity." + sortName + " ");
			if (isDesc) {
				queryString.append("DESC  ");
			}
			// 讀取出所有的資料
			list = this.findByNamedParams(queryString, paramMap);
		} catch (DataAccessException dataAccessException) {

			throw dataAccessException;
		}
		return list;
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#find(java.lang.StringBuffer,
	 *      java.lang.String, boolean, java.util.Map, int, int)
	 */
	@Override
	public List<T> find(StringBuffer queryString, String sortName, boolean isDesc,
			Map<String, ? extends Object> paramMap, int from, int end) {
		List<T> list = null;
		try {

			queryString.append("ORDER BY entity." + sortName + " ");
			if (isDesc) {
				queryString.append("DESC  ");
			}
			// 讀取出所有的資料
			list = this.find(queryString, paramMap, from, end);
		} catch (DataAccessException dataAccessException) {

			throw dataAccessException;
		}
		return list;
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#findAll()
	 */
	@Override
	public List<T> findAll() {
		List<T> list = null;
		try {
			StringBuffer queryString = new StringBuffer();
			queryString.append("SELECT DISTINCT entity FROM " + this.getPersistClass().getSimpleName())
					.append(" entity ");

			// find all data
			list = this.find(queryString);
		} catch (DataAccessException dataAccessException) {

			throw dataAccessException;
		}
		return list;
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#findAll(java.lang.String, boolean)
	 */
	@Override
	public List<T> findAll(String sortName, boolean isDesc) {
		List<T> list = null;
		try {

			StringBuffer queryString = new StringBuffer();
			queryString.append("SELECT DISTINCT entity FROM " + this.getPersistClass().getSimpleName())
					.append(" entity ");
			queryString.append("ORDER BY " + sortName);

			if (isDesc) {
				queryString.append(" DESC  ");

			}
			// find all data
			list = this.find(queryString);
		} catch (DataAccessException dataAccessException) {

			throw dataAccessException;
		}
		return list;
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#findByCriteriaMap(java.util.Map)
	 */
	@Override
	public List<T> findByCriteriaMap(Map<String, ? extends Object> criteriaMap) {

		return this.findByCriteriaMap(criteriaMap, null, null);
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#findByCriteriaMap(java.util.Map,
	 *      java.util.Map)
	 */
	@Override
	public List<T> findByCriteriaMap(Map<String, ? extends Object> criteriaMap, Map<String, String> sortMap) {

		return this.findByCriteriaMap(criteriaMap, null, sortMap);
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#findByCriteriaMap(java.util.Map,
	 *      java.util.Map, java.util.Map)
	 */
	@Override
	public List<T> findByCriteriaMap(Map<String, ? extends Object> criteriaMap, Map<String, String> operMap,
			Map<String, String> sortMap) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT DISTINCT entity FROM " + this.getPersistClass().getSimpleName()).append(" entity ");

		Map<String, Object> newCriteriaMap = new HashMap<String, Object>();

		boolean isTruncateWhereQueryString = false;
		boolean isTruncateSortQueryString = false;
		for (String criteriaKey : criteriaMap.keySet()) {

			if (null == criteriaMap.get(criteriaKey)) {
				continue;
			}
			if (criteriaMap.get(criteriaKey).toString().compareTo("") != 0) {
				if (!isTruncateWhereQueryString) {
					queryString.append(" WHERE ");
				}
				if (operMap != null && operMap.size() > 0) {
					if (operMap.get(criteriaKey.toString()) != null
							&& operMap.get(criteriaKey.toString()).equals("eq")) {
						queryString.append("entity.").append(criteriaKey.toString()).append(" = :")
								.append(criteriaKey.toString().replace(".", "_"));
					} else if (operMap.get(criteriaKey.toString()) != null
							&& operMap.get(criteriaKey.toString()).equals("ge")) {
						queryString.append("entity.").append(criteriaKey.toString()).append(" >= :")
								.append(criteriaKey.toString().replace(".", "_"));
					} else if (operMap.get(criteriaKey.toString()) != null
							&& operMap.get(criteriaKey.toString()).equals("gt")) {
						queryString.append("entity.").append(criteriaKey.toString()).append(" > :")
								.append(criteriaKey.toString().replace(".", "_"));
					} else if (operMap.get(criteriaKey.toString()) != null
							&& operMap.get(criteriaKey.toString()).equals("le")) {
						queryString.append("entity.").append(criteriaKey.toString()).append(" <= :")
								.append(criteriaKey.toString().replace(".", "_"));
					} else if (operMap.get(criteriaKey.toString()) != null
							&& operMap.get(criteriaKey.toString()).equals("lt")) {
						queryString.append("entity.").append(criteriaKey.toString()).append(" < :")
								.append(criteriaKey.toString().replace(".", "_"));
					} else if (operMap.get(criteriaKey.toString()) != null
							&& operMap.get(criteriaKey.toString()).equals("ne")) {
						queryString.append("entity.").append(criteriaKey.toString()).append(" <> :")
								.append(criteriaKey.toString().replace(".", "_"));
					} else if (operMap.get(criteriaKey.toString()) != null
							&& operMap.get(criteriaKey.toString()).equals("like")) {
						queryString.append("entity.").append(criteriaKey.toString()).append(" like :")
								.append(criteriaKey.toString().replace(".", "_"));
					} else {
						queryString.append("entity.").append(criteriaKey.toString()).append(" = :")
								.append(criteriaKey.toString().replace(".", "_"));
					}
				} else {
					queryString.append("entity.").append(criteriaKey.toString()).append(" = :")
							.append(criteriaKey.toString().replace(".", "_"));
				}
				queryString.append(" AND ");

				newCriteriaMap.put(criteriaKey.replace(".", "_"), criteriaMap.get(criteriaKey));
				isTruncateWhereQueryString = true;
			}
		}

		if (isTruncateWhereQueryString) {
			int lastIndex = queryString.lastIndexOf("AND");
			queryString.delete(lastIndex, queryString.length());
		}

		if (sortMap != null) {
			for (String sortKey : sortMap.keySet()) {

				if (null == sortMap.get(sortKey)) {
					continue;
				}
				if (sortMap.get(sortKey).toString().compareTo("") != 0) {
					if (!isTruncateSortQueryString) {
						queryString.append(" ORDER BY ");

					}
					queryString.append("entity.").append(sortKey.toString()).append(" ").append(sortMap.get(sortKey));
					queryString.append(" , ");

					isTruncateSortQueryString = true;
				}
			}

			if (isTruncateSortQueryString) {
				int lastIndex = queryString.lastIndexOf(",");
				queryString.delete(lastIndex, queryString.length());
			}
		}
		return this.findByNamedParams(queryString, newCriteriaMap);
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#findByCriteriaMapReturnUnique(java.util.Map)
	 */
	@Override
	public T findByCriteriaMapReturnUnique(Map<String, ? extends Object> criteriaMap) {
		return findByCriteriaMapReturnUnique(criteriaMap, null, null);
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#findByCriteriaMapReturnUnique(java.util.Map,
	 *      java.util.Map)
	 */
	@Override
	public T findByCriteriaMapReturnUnique(Map<String, ? extends Object> criteriaMap, Map<String, String> sortMap) {
		return findByCriteriaMapReturnUnique(criteriaMap, null, sortMap);
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#findByCriteriaMapReturnUnique(java.util.Map,
	 *      java.util.Map, java.util.Map)
	 */
	@Override
	public T findByCriteriaMapReturnUnique(Map<String, ? extends Object> criteriaMap, Map<String, String> operMap,
			Map<String, String> sortMap) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT DISTINCT entity FROM " + this.getPersistClass().getSimpleName()).append(" entity ");

		Map<String, Object> newCriteriaMap = new HashMap<String, Object>();

		boolean isTruncateWhereQueryString = false;
		boolean isTruncateSortQueryString = false;
		for (String criteriaKey : criteriaMap.keySet()) {

			if (null == criteriaMap.get(criteriaKey)) {
				continue;
			}
			if (criteriaMap.get(criteriaKey).toString().compareTo("") != 0) {
				if (!isTruncateWhereQueryString) {
					queryString.append(" WHERE ");
				}
				if (operMap != null && operMap.size() > 0) {
					if (operMap.get(criteriaKey.toString()) != null
							&& operMap.get(criteriaKey.toString()).equals("eq")) {
						queryString.append("entity.").append(criteriaKey.toString()).append(" = :")
								.append(criteriaKey.toString().replace(".", "_"));
					} else if (operMap.get(criteriaKey.toString()) != null
							&& operMap.get(criteriaKey.toString()).equals("ge")) {
						queryString.append("entity.").append(criteriaKey.toString()).append(" >= :")
								.append(criteriaKey.toString().replace(".", "_"));
					} else if (operMap.get(criteriaKey.toString()) != null
							&& operMap.get(criteriaKey.toString()).equals("gt")) {
						queryString.append("entity.").append(criteriaKey.toString()).append(" > :")
								.append(criteriaKey.toString().replace(".", "_"));
					} else if (operMap.get(criteriaKey.toString()) != null
							&& operMap.get(criteriaKey.toString()).equals("le")) {
						queryString.append("entity.").append(criteriaKey.toString()).append(" <= :")
								.append(criteriaKey.toString().replace(".", "_"));
					} else if (operMap.get(criteriaKey.toString()) != null
							&& operMap.get(criteriaKey.toString()).equals("lt")) {
						queryString.append("entity.").append(criteriaKey.toString()).append(" < :")
								.append(criteriaKey.toString().replace(".", "_"));
					} else if (operMap.get(criteriaKey.toString()) != null
							&& operMap.get(criteriaKey.toString()).equals("ne")) {
						queryString.append("entity.").append(criteriaKey.toString()).append(" <> :")
								.append(criteriaKey.toString().replace(".", "_"));
					} else if (operMap.get(criteriaKey.toString()) != null
							&& operMap.get(criteriaKey.toString()).equals("like")) {
						queryString.append("entity.").append(criteriaKey.toString()).append(" like :")
								.append(criteriaKey.toString().replace(".", "_"));
					} else {
						queryString.append("entity.").append(criteriaKey.toString()).append(" = :")
								.append(criteriaKey.toString().replace(".", "_"));
					}
				} else {
					queryString.append("entity.").append(criteriaKey.toString()).append(" = :")
							.append(criteriaKey.toString().replace(".", "_"));
				}
				queryString.append(" AND ");

				newCriteriaMap.put(criteriaKey.replace(".", "_"), criteriaMap.get(criteriaKey));
				isTruncateWhereQueryString = true;
			}
		}

		if (isTruncateWhereQueryString) {
			int lastIndex = queryString.lastIndexOf("AND");
			queryString.delete(lastIndex, queryString.length());
		}

		if (sortMap != null) {
			for (String sortKey : sortMap.keySet()) {

				if (null == sortMap.get(sortKey)) {
					continue;
				}
				if (sortMap.get(sortKey).toString().compareTo("") != 0) {
					if (!isTruncateSortQueryString) {
						queryString.append(" ORDER BY ");

					}
					queryString.append("entity.").append(sortKey.toString()).append(" ").append(sortMap.get(sortKey));
					queryString.append(" , ");

					isTruncateSortQueryString = true;
				}
			}

			if (isTruncateSortQueryString) {
				int lastIndex = queryString.lastIndexOf(",");
				queryString.delete(lastIndex, queryString.length());
			}
		}
		return this.findByNamedParamsReturnUnique(queryString, newCriteriaMap);
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#findByCriteriaMapWithOper(java.util.Map,
	 *      java.util.Map)
	 */
	@Override
	public List<T> findByCriteriaMapWithOper(Map<String, ? extends Object> criteriaMap, Map<String, String> operMap) {
		return this.findByCriteriaMap(criteriaMap, operMap, null);
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#findByCriteriaMapWithOperReturnUnique(java.util.Map,
	 *      java.util.Map)
	 */
	@Override
	public T findByCriteriaMapWithOperReturnUnique(Map<String, ? extends Object> criteriaMap,
			Map<String, String> operMap) {
		return this.findByCriteriaMapReturnUnique(criteriaMap, operMap, null);
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#findByNamedParams(java.lang.StringBuffer,
	 *      java.util.Map)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByNamedParams(StringBuffer queryString, Map<String, ? extends Object> paramMap) {
		Query query = this.getEntityManager().createQuery(queryString.toString());
		if (paramMap != null) {
			for (String param : paramMap.keySet()) {
				query.setParameter(param, paramMap.get(param));

			}
		}
		return query.getResultList();
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#findByNamedParamsReturnUnique(java.lang.StringBuffer,
	 *      java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T findByNamedParamsReturnUnique(StringBuffer queryString, Map<String, ? extends Object> paramMap) {
		Query query = this.getEntityManager().createQuery(queryString.toString());
		if (paramMap != null) {
			for (String param : paramMap.keySet()) {
				query.setParameter(param, paramMap.get(param));

			}
		}
		return (T) query.getSingleResult();
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#findByNamedQuery(java.lang.StringBuffer)
	 */
	@Override
	public List<T> findByNamedQuery(StringBuffer queryName) {
		return this.findByNamedQuery(queryName, null);
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#findByNamedQuery(java.lang.StringBuffer,
	 *      java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNamedQuery(StringBuffer queryName, Map<String, ? extends Object> paramMap) {
		Query query = this.getEntityManager().createNamedQuery(queryName.toString());
		if (paramMap != null) {
			for (String param : paramMap.keySet()) {
				query.setParameter(param, paramMap.get(param));

			}
		}
		return query.getResultList();

	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#findByPK(java.ientity.Serializable)
	 */
	@Override
	public T findByPK(PK pk) {
		return (T) this.getEntityManager().find(persistClass, pk);
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#findByProperty(java.lang.String,
	 *      java.lang.Object)
	 */
	@Override
	public List<T> findByProperty(String propertyName, Object propertyValue) {
		try {
			StringBuffer queryString = new StringBuffer();
			queryString.append("SELECT DISTINCT entity FROM " + this.getPersistClass().getSimpleName())
					.append(" entity ");
			queryString.append("WHERE entity." + propertyName + " = :propertyValue ");

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("propertyValue", propertyValue);
			return this.findByNamedParams(queryString, params);

		} catch (RuntimeException e) {
			logger.error("find by property name failed", e);
			throw e;
		}
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#findByProperty(java.lang.String,
	 *      java.lang.Object, java.lang.String, boolean)
	 */
	@Override
	public List<T> findByProperty(String propertyName, Object propertyValue, String sortName, boolean isDesc) {
		try {
			StringBuffer queryString = new StringBuffer();
			queryString.append("SELECT DISTINCT entity FROM " + this.getPersistClass().getSimpleName())
					.append(" entity ");
			queryString.append("WHERE entity." + propertyName + " = :propertyValue ");
			queryString.append("ORDER BY entity." + sortName);

			if (isDesc) {
				queryString.append(" DESC  ");

			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("propertyValue", propertyValue);
			return this.findByNamedParams(queryString, params);

		} catch (RuntimeException e) {
			logger.error("find by property name failed", e);
			throw e;
		}
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#getEntityManager()
	 */
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#setEntityManager(javax.persistence.EntityManager)
	 */
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * Get Persist Class
	 *
	 * @return Class<T> Persist Class
	 */
	protected Class<T> getPersistClass() {
		return persistClass;
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#isExist(java.io.Serializable)
	 */
	@Override
	public boolean isExist(PK pk) {
		T entity = this.getEntityManager().find(persistClass, pk);
		boolean result = false;
		if (entity != null) {
			result = true;
		}
		return result;
	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#refresh(java.lang.Object)
	 */
	@Override
	public void refresh(Object obj) {
		this.getEntityManager().refresh(obj);

	}

	/**
	 * @see com.hsuforum.common.dao.BaseDao#update(java.lang.Object)
	 */
	@Override
	public T update(T entity) {

		T result = this.getEntityManager().merge(entity);
		this.getEntityManager().flush();
		return result;

	}

}

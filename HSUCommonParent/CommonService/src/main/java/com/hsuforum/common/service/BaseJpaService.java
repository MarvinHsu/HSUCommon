package com.hsuforum.common.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseJpaService<T, ID extends Serializable> extends Serializable {
	/**
	 * Deletes all entities in a batch call.
	 */
	void deleteAllInBatch();
	/**
	 * Deletes the given entities in a batch which means it will create a single Query.
	 * @param entities
	 */
	void deleteInBatch(Iterable<T> entities);
	/**
	 * find all entities
	 * @return
	 */
	List<T> findAll() ;
	/**
	 * find all entities by ids
	 * @param ids
	 * @return
	 */
	List<T> findAllById(Iterable<ID> ids); 
	/**
	 * Flushes all pending changes to the database.
	 */
	void flush();
	/**
	 * Returns a reference to the entity with the given identifier.
	 * @param id
	 * @return
	 */
	T getOne(ID id);
	/**
	 * save all entities
	 * @param entities
	 * @return
	 */
	<S extends T> List<S> saveAll(Iterable<S> entities);
	/**
	 * Saves an entity and flushes changes instantly.
	 * @param entity
	 * @return
	 */
	<S extends T> S saveAndFlush(S entity);
	
	/**
	 * Saves a given entity.
	 * @param entity
	 */
	<S extends T> S save(S entity);

}

package com.hsuforum.common.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

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
	 * Deletes entitie.
	 * @param entities
	 */
	void delete(T entity);
	/**
	 * find all entities
	 * @return
	 */
	List<T> findAll() ;
	/**
	 * find all entities by example
	 * @param example
	 * @return
	 */
	List<T> findAll(Example<T> example);
	/**
	 * find all entities and pageable by example 
	 * @param example
	 * @param pageable
	 * @return
	 */
	Page<T> findAll(Example<T> example, Pageable pageable);
	/**
	 * find all entities and sort by sample 
	 * @param example
	 * @param sort
	 * @return
	 */
	List<T> findAll(Example<T> example, Sort sort) ;
	/**
	 * find all entities by spec
	 * @return
	 */
	List<T> findAll(Specification<T> spec) ;
	/**
	 * find all entities and pageable by spec 
	 * @return
	 */
	Page<T> findAll(Specification<T> spec, Pageable pageable) ;
	/**
	 * find all entities and sort by spec 
	 * @return
	 */
	List<T> findAll(Specification<T> spec, Sort sort) ;
	/**
	 * find all entities by ids
	 * @param ids
	 * @return
	 */
	List<T> findAllByIds(Iterable<ID> ids); 
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
	 * Returns a reference to the entity with the given example.
	 * @param example
	 * @return
	 */
	Optional<T> findOne(Example<T> example);
	/**
	 * Returns a reference to the entity with the given spec.
	 * @param spec
	 * @return
	 */
	Optional<T> findOne(Specification<T> spec);
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
	/**
	 * Count
	 * @return
	 */
	long count();
	/**
	 * Count by spec
	 * @return
	 */
	long count(Specification<T> spec);
	/**
	 * Count by exapmle
	 * @return
	 */
	long count(Example<T> example);

}

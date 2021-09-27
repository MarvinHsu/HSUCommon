package com.hsuforum.common.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.hsuforum.common.dao.BaseJpaRepository;
import com.hsuforum.common.entity.BaseEntity;
import com.hsuforum.common.service.BaseJpaService;

public abstract class BaseJpaServiceImpl<T extends BaseEntity<ID>, ID extends Serializable, REPO extends BaseJpaRepository<T, ID>>
		implements BaseJpaService<T, ID> {

	private static final long serialVersionUID = 3784804606624047248L;
	
	/**
	 * get repository
	 * @return
	 */
	public abstract REPO getRepo();
	/**
	 * set repository
	 * @param repo
	 */
	public abstract void setRepo(REPO repo);
	
	@Override
	public void deleteAllInBatch() {
		this.getRepo().deleteAllInBatch();
	}
	@Override
	public void deleteInBatch(Iterable<T> entities) {
		this.getRepo().deleteInBatch(entities);
	}
	@Override
	public void delete(T entity) {
		this.getRepo().delete(entity);
	}
	@Override
	public List<T> findAll() {
		return this.getRepo().findAll();
	}
	@Override
	public List<T> findAll(Example<T> example) {
		return this.getRepo().findAll(example);
	}
	@Override
	public Page<T> findAll(Example<T> example, Pageable pageable) {
		return this.getRepo().findAll(example, pageable);
	}
	@Override
	public List<T> findAll(Example<T> example, Sort sort) {
		return this.getRepo().findAll(example, sort);
	}
	@Override
	public List<T> findAll(Specification<T> spec) {
		return this.getRepo().findAll(spec);
	}
	@Override
	public Page<T> findAll(Specification<T> spec, Pageable pageable) {
		return this.getRepo().findAll(spec, pageable);
	}
	@Override
	public List<T> findAll(Specification<T> spec, Sort sort) {
		return this.getRepo().findAll(spec, sort);
	}
	@Override
	public List<T> findAllByIds(Iterable<ID> ids) {
		return this.getRepo().findAllById(ids);
	}
	@Override
	public void flush() {
		this.getRepo().flush();
	}
	@Override
	public T getOne(ID id) {
		return this.getRepo().getOne(id);
	}
	@Override
	public Optional<T> findOne(Specification<T> spec) {
		return this.getRepo().findOne(spec);
	}
	@Override
	public Optional<T> findOne(Example<T> example) {
		return this.getRepo().findOne(example);
	}
	@Override
	public <S extends T> List<S> saveAll(Iterable<S> entities) {
		return this.getRepo().saveAll(entities);
	}
	@Override
	public <S extends T> S saveAndFlush(S entity) {
		return this.getRepo().saveAndFlush(entity);
	}
	@Override
	public <S extends T> S save(S entity) {
		return this.getRepo().save(entity);
	}
	@Override 
	public long count() {
		return this.getRepo().count();	
	}
	@Override 
	public long count(Specification<T> spec) {
		return this.getRepo().count(spec);	
	}
	@Override 
	public long count(Example<T> example) {
		return this.getRepo().count(example);
	}
}

package com.hsuforum.common.service.impl;

import java.io.Serializable;
import java.util.List;

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
	public List<T> findAll() {
		return this.getRepo().findAll();
	}
	@Override
	public List<T> findAllById(Iterable<ID> ids) {
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
	
}

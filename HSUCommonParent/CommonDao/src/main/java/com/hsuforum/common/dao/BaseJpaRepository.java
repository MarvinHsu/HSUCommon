package com.hsuforum.common.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;


public interface BaseJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID>{

}

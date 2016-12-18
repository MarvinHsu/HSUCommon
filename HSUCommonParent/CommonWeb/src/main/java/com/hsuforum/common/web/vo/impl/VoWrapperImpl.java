package com.hsuforum.common.web.vo.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.hsuforum.common.entity.BaseEntity;
import com.hsuforum.common.web.vo.ValueObject;
import com.hsuforum.common.web.vo.VoWrapper;

/**
 * Value object wrapper implement
 * @author Marvin
 *
 * @param <T>
 * @param <PK>
 */
public class VoWrapperImpl<T extends BaseEntity<PK>, PK extends Serializable> implements VoWrapper<T, PK>{

	private static final long serialVersionUID = 7738589783328633615L;

	
	/**
	 * @see com.hsuforum.common.web.vo.VoWrapper#wrap(com.hsuforum.common.entity.BaseEntity)
	 */
	public ValueObject<T, PK> wrap(T entity) {
		return new ValueObjectImpl<T, PK>(entity);
		
	}
	
	
	/**
	 * @see com.hsuforum.common.web.vo.VoWrapper#wrap(java.util.List)
	 */
	public List<ValueObject<T, PK>> wrap(List<T> list) {
		
		if (list != null) {
			List<ValueObject<T, PK>> wrapList = new ArrayList<ValueObject<T, PK>>();
			for(T entity : list){
				ValueObject<T, PK> vo= new ValueObjectImpl<T, PK>(entity);
				wrapList.add(vo);
			}
			return wrapList;
		}else{
			return null;
		}
		
	}

}

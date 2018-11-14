package com.balan.core.repository;

import com.balan.core.entity.GenericEntity;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;

import java.io.Serializable;

public interface GenericRepository<E extends GenericEntity<PK>, PK extends Serializable> extends Mapper<E>, IdsMapper<E> {
}

package com.balan.core.service;

import com.balan.core.entity.GenericEntity;
import com.balan.core.result.page.PageRequest;
import com.balan.core.result.search.SearchFilter;
import com.github.pagehelper.PageInfo;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface IGenericService<E extends GenericEntity<PK>, PK extends Serializable> {

    List<E> findAll();

    List<E> findByExample(Example o);

    E findById(PK id);

    E save(E entity);

    <S extends E> List<S> batchSave(Iterable<S> entities);

    E update(E e);

    int delete(String ids);

    /**
     * 查询分页
     * @param filters 条件
     * @param pageRequest 分页参数
     * @return
     */
    PageInfo<E> pageBySearchFilter(Collection<SearchFilter> filters, PageRequest pageRequest);

    /**
     * 查询不带分页
     * @param filters 条件 具体参考SearchFilter
     * @return
     */
    List<E> findBySearchFilter(Collection<SearchFilter> filters);
}

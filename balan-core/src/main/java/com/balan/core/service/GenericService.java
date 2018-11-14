package com.balan.core.service;

import com.balan.core.entity.GenericEntity;
import com.balan.core.repository.GenericRepository;
import com.balan.core.result.page.Order;
import com.balan.core.result.page.PageRequest;
import com.balan.core.result.search.DynamicSpecifications;
import com.balan.core.result.search.SearchFilter;
import com.balan.core.utils.ReflectUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 通用抽象service
 *
 * @param <E>
 * @param <PK>
 */
@Slf4j
public abstract class GenericService<E extends GenericEntity<PK>, PK extends Serializable> implements IGenericService<E, PK> {


    protected final Class<E> tClass;

    public GenericService() {
        tClass = ReflectUtils.getInstance().getClass(getClass().getGenericSuperclass(), 0);
    }

    protected abstract GenericRepository<E, PK> getRepository();

    @Override
    public List<E> findAll() {
        return getRepository().selectAll();

    }

    @Override
    public List<E> findByExample(Example o) {
        return getRepository().selectByExample(o);
    }

    @Override
    public E findById(PK id) {
        return getRepository().selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public E save(E entity) {
        Objects.requireNonNull(entity);
        int result = getRepository().insert(entity);
        return result > 0 ? entity : null;
    }

    @Override
    @Transactional
    public <S extends E> List<S> batchSave(Iterable<S> entities) {
        List<S> result = new ArrayList<S>();

        if (entities == null) {
            return result;
        }
        entities.forEach(entity -> {
            getRepository().insert(entity);
            result.add(entity);
        });
        return result;
    }

    @Override
    public E update(E e) {
        return getRepository().updateByPrimaryKey(e) > 0 ? e : null;
    }

    @Override
    public int delete(String ids) {
        int i = getRepository().deleteByIds(ids);
        return i;
    }

    @Override
    public PageInfo<E> pageBySearchFilter(Collection<SearchFilter> filters, PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum() == null ? 1 : pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize() == null ? 20 : pageRequest.getPageSize();

        PageHelper.startPage(pageNum, pageSize);

        String order = pageRequest.getOrder(); // asc OR desc
        String sort = pageRequest.getSort();  // 排序
        if (StringUtils.isEmpty(sort)) {
            pageRequest.setOpenSort(false);
        } else {
            pageRequest.setOpenSort(true);
            pageRequest.setAsc(Objects.equals(Order.ASC.value(), order) ? true : false);
        }
        if (pageRequest.isOpenSort()) {
            PageHelper.orderBy(sort + " " + order);
        }
        Example example = DynamicSpecifications.bySearchFilter(tClass, filters);
        PageInfo<E> result = new PageInfo<>(getRepository().selectByExample(example));
        return result;
    }

    @Override
    public List<E> findBySearchFilter(Collection<SearchFilter> filters) {
        return getRepository().selectByExample(DynamicSpecifications.bySearchFilter(tClass, filters));
    }
}

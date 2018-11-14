package com.balan.core.controller;

import com.balan.core.entity.GenericEntity;
import com.balan.core.result.page.PageRequest;
import com.balan.core.result.response.ResponseResult;
import com.balan.core.result.search.SearchFilter;
import com.balan.core.service.IGenericService;
import com.balan.core.utils.ReflectUtils;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

/**
 * 抽象json控制器
 * @param <E>
 * @param <PK>
 */
@Slf4j
public abstract class GenericController<E extends GenericEntity<PK>, PK extends Serializable> {

    protected final Class<E> eClass;

    protected abstract IGenericService<E, PK> getService();

    GenericController(){
        this.eClass = ReflectUtils.getInstance().getClass(getClass().getGenericSuperclass(), 0);
    }

    /**
     * 分页
     * @param pageRequest
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/page")
    protected PageInfo<E> page(PageRequest pageRequest, HttpServletRequest request) throws Exception {
        List<SearchFilter> filters = SearchFilter.parse(request);
        prePage(pageRequest, request, filters);
        return getService().pageBySearchFilter(filters, pageRequest);
    }

    protected void prePage(PageRequest pageRequest, HttpServletRequest request, List<SearchFilter> filters) {
    }

    /**
     * 列表
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/list")
    protected ResponseResult<List<E>> list(HttpServletRequest request) throws Exception {
        List<SearchFilter> filters = SearchFilter.parse(request);
        prelist(request, filters);
        return ResponseResult.ok(getService().findBySearchFilter(filters));
    }

    protected void prelist(HttpServletRequest request, List<SearchFilter> filters) {
    }


}

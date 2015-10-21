package com.edgar.module.sys.service;

import com.edgar.core.repository.Pagination;
import com.edgar.core.repository.QueryExample;
import com.edgar.module.sys.repository.domain.SysResource;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Rest资源的业务逻辑
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Validated
public interface SysResourceService {

    /**
     * 查询所有资源
     *
     * @return 资源的集合
     */
    @NotNull
    List<SysResource> findAll();

    /**
     * 分页查询资源
     *
     * @param example  查询条件
     * @param page     当前页
     * @param pageSize 每页记录数
     * @return 资源的分页类
     */
    @NotNull
    Pagination<SysResource> pagination(@NotNull QueryExample example, @Min(1) int page,
                                       @Min(1) int pageSize);

    /**
     * 根据资源ID查询资源
     *
     * @param resourceId 资源ID
     * @return 资源
     */
    SysResource get(@Min(1) int resourceId);
}

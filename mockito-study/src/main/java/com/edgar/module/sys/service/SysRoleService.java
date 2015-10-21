package com.edgar.module.sys.service;

import com.edgar.core.repository.Pagination;
import com.edgar.core.repository.QueryExample;
import com.edgar.module.sys.repository.domain.SysRole;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色的业务逻辑接口
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Validated
public interface SysRoleService {

    /**
     * 新增角色
     *
     * @param sysRole 角色
     */
    void save(@NotNull SysRole sysRole);

    /**
     * 修改角色
     *
     * @param sysRole 角色
     */
    void update(@NotNull SysRole sysRole);

    /**
     * 根据角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色
     */
    SysRole get(int roleId);

    /**
     * 分页查询角色
     *
     * @param example  查询条件
     * @param page     当前页
     * @param pageSize 每页记录数
     * @return 角色的分页类
     */
    @NotNull
    Pagination<SysRole> pagination(QueryExample example, int page, int pageSize);

    /**
     * 查询角色的集合
     *
     * @param example 查询条件
     * @return 角色集合
     */
    @NotNull
    List<SysRole> query(QueryExample example);

    /**
     * 根据角色ID和时间戳删除角色
     *
     * @param roleId      角色ID
     * @param updatedTime 时间戳
     */
    void deleteWithLock(@Min(1) int roleId, @NotNull long updatedTime);

}

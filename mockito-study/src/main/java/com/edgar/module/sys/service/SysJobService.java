package com.edgar.module.sys.service;

import com.edgar.core.repository.Pagination;
import com.edgar.core.repository.QueryExample;
import com.edgar.module.sys.repository.domain.SysJob;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 作业的业务逻辑接口
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Validated
public interface SysJobService {
    /**
     * 新增作业
     *
     * @param sysJob 作业
     */
    void save(@NotNull SysJob sysJob);

    /**
     * 修改作业
     *
     * @param sysJob 作业
     */
    void update(@NotNull SysJob sysJob);

    /**
     * 根据作业ID查询作业
     *
     * @param jobId 作业ID
     * @return 作业
     */
    SysJob get(int jobId);

    /**
     * 分页查询作业
     *
     * @param example  查询条件
     * @param page     当前页
     * @param pageSize 每页记录数
     * @return 作业的分页类
     */
    @NotNull
    Pagination<SysJob> pagination(QueryExample example, int page, int pageSize);

    /**
     * 查询作业的集合
     *
     * @param example 查询条件
     * @return 作业集合
     */
    @NotNull
    List<SysJob> query(QueryExample example);

    /**
     * 根据作业ID和时间戳删除作业
     *
     * @param jobId       作业ID
     * @param updatedTime 时间戳
     */
    void deleteWithLock(@Min(1) int jobId, @NotNull long updatedTime);

    /**
     * 校验作业类是否已经存储
     *
     * @param clazzName 类名
     * @return 如果存在，则返回false
     */
    boolean checkClazzName(@NotEmpty String clazzName);

    /**
     * 查询所有未禁用的作业
     *
     * @return 作业列表
     */
    List<SysJob> findEnabledJob();
}

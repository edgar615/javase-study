package com.edgar.module.sys.service;

import com.edgar.core.repository.Pagination;
import com.edgar.core.repository.QueryExample;
import com.edgar.module.sys.repository.domain.I18nMessage;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * 国际化的业务逻辑接口
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Validated
public interface I18nMessageService {
    /**
     * 新增i18n
     *
     * @param i18n i18n
     */
    void save(@NotNull I18nMessage i18n);

    /**
     * 修改i18n
     *
     * @param i18n i18n
     */
    void update(@NotNull I18nMessage i18n);

    /**
     * 根据i18nID查询i18n
     *
     * @param i18nId i18nID
     * @return i18n
     */
    I18nMessage get(int i18nId);

    /**
     * 分页查询i18n
     *
     * @param example  查询条件
     * @param page     当前页
     * @param pageSize 每页记录数
     * @return i18n的分页类
     */
    @NotNull
    Pagination<I18nMessage> pagination(QueryExample example, int page, int pageSize);

    /**
     * 根据i18nID和时间戳删除i18n
     *
     * @param i18nId      i18nID
     * @param updatedTime 时间戳
     */
    void deleteWithLock(@Min(1) int i18nId, @NotNull long updatedTime);

    /**
     * 校验i18n类是否已经存储
     *
     * @param key 键值
     * @return 如果存在，则返回false
     */
    boolean checkKey(@NotEmpty String key);

    /**
     * 将国际化文件写入Json
     *
     * @throws java.io.IOException
     */
    void saveToJsonFile() throws IOException;

}

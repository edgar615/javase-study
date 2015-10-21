package com.edgar.core.helper;

import com.edgar.module.sys.repository.domain.SysDict;
import org.springframework.context.ApplicationEvent;

/**
 * 字典的spring事件
 *
 * @author Edgar Zhang
 * @version 1.0
 */
public final class SysDictEvent extends ApplicationEvent {

    private static final long serialVersionUID = -7326179730504859642L;

    private SysDictEvent(SysDict source) {
        super(source);
    }

    /**
     * 创建一个新增和更新事件
     *
     * @param sysDict 字典
     * @return 字典事件
     */
    public static SysDictEvent newEvent(SysDict sysDict) {
        return new SysDictEvent(sysDict);
    }

}
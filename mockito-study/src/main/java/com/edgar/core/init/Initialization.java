package com.edgar.core.init;

import com.edgar.core.exception.SystemException;
import org.springframework.core.Ordered;

/**
 * 初始化类的接口，如果需要在系统启动时执行某个方法，需要实现此接口.
 *
 * @author Edgar
 * @version 1.0
 */
public interface Initialization extends Ordered {

    /**
     * 初始化方法.
     *
     * @throws SystemException 系统异常
     */
    void init() throws SystemException;
}

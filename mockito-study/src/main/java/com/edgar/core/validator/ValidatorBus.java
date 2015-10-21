package com.edgar.core.validator;

/**
 * Created by Administrator on 14-9-11.
 */
public interface ValidatorBus {

    /**
     * 校验对象是否合法
     *
     * @param target        需要校验的对象
     * @param strategyClass 校验类
     */
    void validator(Object target, Class<? extends ValidatorStrategy> strategyClass);
}

package com.edgar.core.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Spring Bean的工具类.
 *
 * @author Edgar
 * @version 1.0
 */
@Service
public class ServiceLookup implements ApplicationContextAware {

    private static ApplicationContext APPLICATION_CONTEXT;

    public void setApplicationContext(ApplicationContext applicationContext) {
        ServiceLookup.APPLICATION_CONTEXT = applicationContext;
    }

    /**
     * 根据Bean的名称查找Spring Bean
     *
     * @param beanName 名称
     * @param clazz    类型
     * @param <T>      泛型
     * @return Spring Bean
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        return APPLICATION_CONTEXT.getBean(beanName, clazz);
    }

    /**
     * 根据Bean的类型查找Spring Bean
     *
     * @param clazz 类型
     * @param <T>   泛型
     * @return Spring Bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return APPLICATION_CONTEXT.getBean(clazz);
    }
}
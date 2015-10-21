package com.edgar.core.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;

/**
 * Spring事件的工具类
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Service
public class EventUtils implements ApplicationContextAware {

    private static ApplicationContext APPLICATION_CONTEXT;

    public void setApplicationContext(ApplicationContext applicationContext) {
        APPLICATION_CONTEXT = applicationContext;
    }

    /**
     * 发布一个事件
     *
     * @param event 事件
     */
    public static void publishEvent(ApplicationEvent event) {
        APPLICATION_CONTEXT.publishEvent(event);
    }
}
package com.edgar.core.shiro;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 资源的注解，用在springMVC的方法上，在系统初次启动时创建资源
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuthHelper {
    String value();

    boolean isRoot() default false;

    AuthType type() default AuthType.REST;
}

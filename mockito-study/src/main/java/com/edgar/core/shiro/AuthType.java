package com.edgar.core.shiro;

/**
 * 资源的权限控制类别
 *
 * @author Edgar Zhang
 * @version 1.0
 */
public enum AuthType {

    /**
     * 不通过权限认证
     */
    ANON,
    /**
     * 用户需要登录
     */
    AUTHC,
    /**
     * 用户需要有rest授权
     */
    REST,

    /**
     * 请求是https
     */
    SSL
}

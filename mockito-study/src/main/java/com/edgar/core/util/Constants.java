package com.edgar.core.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 全局变量.
 *
 * @author Edgar
 * @version 1.0
 */
public abstract class Constants {

    public static final ExecutorService EXEC = Executors.newCachedThreadPool();

    public static final String USER_KEY = "USER";

    /**
     * 默认语言
     */
    public static final String DEFAULT_PROFILE_LANG = "en";

    /**
     * 超级管理员的名称
     */
    public static final String ROOT_ROLE_NAME = "root";

    /**
     * anon类型的权限
     */
    public static final String AUTH_TYPE_ANON = "anon";

    /**
     * authc类型的权限
     */
    public static final String AUTH_TYPE_AUTHC = "authc";

    /**
     * rest类型的权限
     */
    public static final String AUTH_TYPE_REST = "rest";

    /**
     * ssl类型的权限
     */
    public static final String AUTH_TYPE_SSL = "ssl";

    /**
     * 加密算法
     */
    public static final String TOKEN_ALGORITHM_NAME = "MD5";

    /**
     * 迭代次数
     */
    public static final int TOKEN_HASH_ITERATIONS = 1024;

}

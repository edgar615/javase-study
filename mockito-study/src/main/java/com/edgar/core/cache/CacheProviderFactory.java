package com.edgar.core.cache;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-9
 * Time: 下午4:56
 * To change this template use File | Settings | File Templates.
 */
public interface CacheProviderFactory {

    public CacheProvider createCacheWrapper(String cacheName);
}

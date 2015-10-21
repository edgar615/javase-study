package com.edgar.core.cache;

import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-9
 * Time: 下午4:57
 * To change this template use File | Settings | File Templates.
 */
@Service
public class EhCacheProviderFactory implements CacheProviderFactory {

    @Autowired
    private CacheManager cacheManager;

    @Override
    public CacheProvider createCacheWrapper(String cacheName) {
        return new EhCacheProvider(cacheName, cacheManager);
    }
}

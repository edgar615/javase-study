package com.edgar.core.cache;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * EhCache缓存的实现
 *
 * @param <K> 缓存的键值
 * @param <V> 缓存的值
 * @author Edgar Zhang
 * @version 1.0
 */
public class EhCacheProvider<K, V> implements CacheProvider<K, V> {
    private final String cacheName;
    private final CacheManager cacheManager;

    private Ehcache cache;

    protected EhCacheProvider(final String cacheName, final CacheManager cacheManager) {
        this.cacheName = cacheName;
        this.cacheManager = cacheManager;
        this.cache = cacheManager.getEhcache(cacheName);

    }

    @Override
    public void put(K key, V value) {
        cache.put(new Element(key, value));
    }

    @Override
    public void put(K key, V value, int expiresInSec) {
        Element element = new Element(key, value);
        element.setTimeToLive(expiresInSec);
        cache.put(new Element(key, value));
    }

    @SuppressWarnings("unchecked")
    @Override
    public V get(K key) {
        Element element = cache.get(key);
        if (element != null) {
            return (V) element.getObjectValue();
        }
        return null;
    }

    @Override
    public void remove(K key) {
        cache.remove(key);
    }

    @Override
    public void removeAll() {
        cache.removeAll();
    }

}
package com.edgar.core.cache;

/**
 * 缓存的接口.
 *
 * @param <K> 缓存的键值
 * @param <V> 缓存的值
 * @author Edgar Zhang
 * @version 1.0
 */
public interface CacheProvider<K, V> {

    /**
     * 增加缓存
     *
     * @param key   键
     * @param value 值
     */
    void put(K key, V value);

    /**
     * 读取缓存
     *
     * @param key 键
     * @return 值
     */
    V get(K key);

    /**
     * 删除缓存
     *
     * @param key 键
     */
    void remove(K key);

    /**
     * 删除所有缓存
     */
    void removeAll();

    void put(K key, V value, int expiresInSec);
}
package com.edgar.core.cache;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;

/**
 * Created by Administrator on 14-9-15.
 */
@Service
public class RedisProvider {

    private JedisPool jedisPool;

    @Value("${redis.pool.maxIdle}")
    private String maxIdle;

    @Value("${redis.pool.maxTotal}")
    private String maxTotal;

    @Value("${redis.pool.testOnBorrow}")
    private String testOnBorrow;

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private String port;

    @PostConstruct
    public void init() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(NumberUtils.toInt(maxIdle));
        poolConfig.setMaxTotal(NumberUtils.toInt(maxTotal));
        poolConfig.setTestOnBorrow(BooleanUtils.toBoolean(testOnBorrow));
        poolConfig.setMaxWaitMillis(100000);
        jedisPool = new JedisPool(poolConfig, host, NumberUtils.toInt(port));
    }

    public void put(String key, Object value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(RedisUtils.getKey(key), RedisUtils.object2Bytes(value));
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public Object get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            byte[] value = jedis.get(RedisUtils.getKey(key));
            return RedisUtils.byte2Object(value);
        } finally {
            jedisPool.returnResource(jedis);
        }

    }

    public void remove(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(RedisUtils.getKey(key));
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public void removeAll() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.flushAll();
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public void put(String key, Object value, int expiresInSec) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.setex(RedisUtils.getKey(key), expiresInSec, RedisUtils.object2Bytes(value));
        } finally {
            jedisPool.returnResource(jedis);
        }
    }
}

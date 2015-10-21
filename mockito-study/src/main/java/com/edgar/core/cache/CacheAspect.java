package com.edgar.core.cache;

import com.edgar.core.concurrency.StripedLock;
import com.edgar.core.repository.AbstractDaoTemplate;
import com.edgar.core.repository.QueryExample;
import com.mysema.query.sql.RelationalPathBase;
import com.mysema.query.types.Path;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EhCache的AOP类，拦截持久层的get,update,delete方法并建立缓存 其中<code>update(QueryExample)</code>和
 * <code>delete(QueryExample)</code>方法会删除所有缓存
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Aspect
@Service
public class CacheAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheAspect.class);

    @Autowired
    private CacheProviderFactory cacheProviderFactory;

    private static final StripedLock LOCK = new StripedLock(6);

    /**
     * get的切面.
     *
     * @param pk 主键
     */
    @Pointcut("execution(* com.edgar.core.repository.BaseDao+.get(Object)) && args(pk)")
    public void getPointCut(Object pk) {
    }

    /**
     * 插入记录的切面.
     *
     * @param domain 实体对象
     */
    @Pointcut("execution(* com.edgar.core.repository.BaseDao+.insert(Object)) && args(domain)")
    public void insertPointCut(Object domain) {

    }

    /**
     * 插入记录的切面.
     *
     * @param domains 实体对象
     */
    @SuppressWarnings("rawtypes")
    @Pointcut("execution(* com.edgar.core.repository.BaseDao+.insert(java.util.List)) && args(domains)")
    public void insertListPointCut(List domains) {

    }

    /**
     * 根据主键修改记录的切面.
     *
     * @param domain 实体对象
     */
    @Pointcut("execution(* com.edgar.core.repository.BaseDao+.update(Object)) && args(domain)")
    public void updatePointCut(Object domain) {

    }

    /**
     * 根据主键和时间戳修改记录的切面.
     *
     * @param domain 实体对象
     */
    @Pointcut("execution(* com.edgar.core.repository.BaseDao+.updateByVersion(Object)) && args(domain)")
    public void updateByVersionPointCut(Object domain) {

    }

    /**
     * 根据条件修改记录的切面.
     *
     * @param domain  实体对象
     * @param example 查询条件
     */
    @Pointcut(value = "execution(* com.edgar.core.repository.BaseDao+.update(Object, com.edgar.core.repository.QueryExample)) && args(domain, example)", argNames = "domain,example")
    public void updateByExamplePointCut(Object domain, QueryExample example) {

    }

    /**
     * 根据主键删除记录的切面.
     *
     * @param pk 主键
     */
    @Pointcut("execution(* com.edgar.core.repository.BaseDao+.deleteByPk(Object)) && args(pk)")
    public void deletePointCut(Object pk) {

    }

    /**
     * 根据主键和时间戳删除记录的切面.
     *
     * @param pk          主键
     * @param updatedTime 时间戳
     */
    @Pointcut(value = "execution(* com.edgar.core.repository.BaseDao+.deleteByPkAndVersion(Object, long)) && args(pk, updatedTime)", argNames = "pk,updatedTime")
    public void deleteByVersionPointCut(Object pk, long updatedTime) {

    }

    /**
     * 根据条件删除记录的切面.
     */
    @Pointcut("execution(* com.edgar.core.repository.BaseDao+.delete(com.edgar.core.repository.QueryExample)) && args(example)")
    public void deleteByExamplePointCut(QueryExample example) {

    }

    /**
     * get方法的拦截
     *
     * @param jp 拦截点
     * @param pk 主键
     * @return 实体类
     * @throws Throwable 异常
     */
    @SuppressWarnings("rawtypes")
    @Around(value = "getPointCut(pk)")
    public Object aroundGet(ProceedingJoinPoint jp, Object pk) throws Throwable {
        String cacheName = getCacheName(jp);
        CacheProvider cacheProvider = getCacheWrapper(cacheName);
        Object key = getCacheKey(jp, pk);
        Object value = cacheProvider.get(key);
        if (value != null) {
            LOGGER.debug("get value from cache: {},key: {}", cacheName,
                    key);
            return value;
        } else {
            try {
                LOCK.lock(key.hashCode());
                value = cacheProvider.get(key);
                if (value != null) {
                    LOGGER.debug("get value from cache: {},key: {}", cacheName,
                            key);
                    return value;
                } else {
                    value = jp.proceed(jp.getArgs());
                    cacheProvider.put(key, value);
                    LOGGER.debug("get value from db,key:{}", key);
                }
            } finally {
                LOCK.unlock(key.hashCode());
            }
            return value;
        }
    }

    /**
     * 插入记录的拦截
     *
     * @param jp     拦截点
     * @param domain 实体对象
     * @throws Throwable 异常
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @After(value = "insertPointCut(domain)")
    public void afterInsert(JoinPoint jp, Object domain) throws Throwable {

        AbstractDaoTemplate t = (AbstractDaoTemplate) jp.getTarget();
        String cacheName = t.getEntityBeanType().getSimpleName() + "Cache";
        CacheProvider cacheProvider = getCacheWrapper(cacheName);
        Object pk = getPrimaryKeyValue(t.getPathBase(), domain);
        Object key = getCacheKey(jp, pk);
        cacheProvider.put(key, t.get(pk));
        LOGGER.debug("put value into cache: {},key:{}", cacheName, key);
    }

    /**
     * 插入记录的拦截
     *
     * @param jp      拦截点
     * @param domains 实体对象的集合
     * @throws Throwable 异常
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @After(value = "insertListPointCut(domains)")
    public void afterInsertList(JoinPoint jp, List domains) throws Throwable {

        AbstractDaoTemplate t = (AbstractDaoTemplate) jp.getTarget();
        String cacheName = t.getEntityBeanType().getSimpleName() + "Cache";
        CacheProvider cacheProvider = getCacheWrapper(cacheName);
        for (Object obj : domains) {
            Object pk = getPrimaryKeyValue(t.getPathBase(), obj);
            Object key = getCacheKey(jp, pk);
            cacheProvider.put(key, t.get(pk));
            LOGGER.debug("put value into cache: {},key:{}", cacheName, key);
        }
    }

    /**
     * 根据主键修改记录的拦截
     *
     * @param jp     拦截点
     * @param domain 实体对象
     * @throws Throwable 异常
     */
    @After(value = "updatePointCut(domain)")
    public void afterUpdate(JoinPoint jp, Object domain) throws Throwable {
        updateCache(jp, domain);
    }

    /**
     * 根据查询条件修改记录后的拦截
     *
     * @param jp      拦截点
     * @param domain  实体对象
     * @param example 查询条件
     * @throws Throwable 异常
     */
    @After(value = "updateByExamplePointCut(domain, example)")
    public void afterUpdateByExample(JoinPoint jp, Object domain, QueryExample example)
            throws Throwable {
        removeAllCache(jp);
    }

    /**
     * 根据主键和时间戳修改记录的拦截
     *
     * @param jp     拦截点
     * @param domain 实体对象
     * @throws Throwable 异常
     */
    @After(value = "updateByVersionPointCut(domain)")
    public void afterUpdateByVersion(JoinPoint jp, Object domain) throws Throwable {
        updateCache(jp, domain);
    }

    /**
     * 根据主键删除记录后的拦截
     *
     * @param jp 拦截点
     * @param pk 主键
     * @throws Throwable 异常
     */
    @After(value = "deletePointCut(pk)")
    public void afterDeletePointCut(JoinPoint jp, Object pk) throws Throwable {
        deleteCache(jp, pk);
    }

    /**
     * 根据主键和时间戳删除记录后的拦截
     *
     * @param jp          拦截点
     * @param pk          主键
     * @param updatedTime 时间戳
     * @throws Throwable 异常
     */
    @After(value = "deleteByVersionPointCut(pk, updatedTime)")
    public void afterDeleteByVersionPointCut(JoinPoint jp, Object pk, long updatedTime)
            throws Throwable {
        deleteCache(jp, pk);
    }

    /**
     * 根据查询条件删除记录后的拦截
     *
     * @param jp      拦截点
     * @param example 查询条件
     * @throws Throwable 异常
     */
    @After(value = "deleteByExamplePointCut(example)")
    public void afterDeleteByExamplePointCut(JoinPoint jp, QueryExample example)
            throws Throwable {
        removeAllCache(jp);
    }

    /**
     * 更新cache
     *
     * @param jp     拦截点
     * @param domain 实体对象
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private void updateCache(JoinPoint jp, Object domain) {

        AbstractDaoTemplate t = (AbstractDaoTemplate) jp.getTarget();
        String cacheName = t.getEntityBeanType().getSimpleName() + "Cache";
        CacheProvider cacheProvider = getCacheWrapper(cacheName);
        Object pk = getPrimaryKeyValue(t.getPathBase(), domain);
        Object key = getCacheKey(jp, pk);
        Object value = t.get(pk);
        cacheProvider.put(key, value);
        LOGGER.debug("update value in cache: {},key:{}", cacheName, key);
    }

    /**
     * 根据主键删除cache
     *
     * @param jp 拦截点
     * @param pk 主键
     */
    @SuppressWarnings("rawtypes")
    private void deleteCache(JoinPoint jp, Object pk) {
        String cacheName = getCacheName(jp);
        CacheProvider cacheProvider = getCacheWrapper(cacheName);
        Object key = getCacheKey(jp, pk);
        cacheProvider.remove(key);
        LOGGER.debug("remove from cache: {},key:{}", cacheName, key);
    }

    /**
     * 删除所有的cache
     *
     * @param jp 拦截点
     */
    @SuppressWarnings("rawtypes")
    private void removeAllCache(JoinPoint jp) {
        String cacheName = getCacheName(jp);
        CacheProvider cacheProvider = getCacheWrapper(cacheName);
        cacheProvider.removeAll();
        LOGGER.debug("clear cache: {}", cacheName);
    }


    /**
     * 根据主键生成ehcache的键值，如果是primitive,String ,CharSequence,Number,Date,URI,URL,
     * Locale,Class，则使用主键，其他类型的主键使用格式化后的字符串
     *
     * @param pk 主键
     * @return cache的键值
     */

    private String getCacheKey(JoinPoint jp, Object pk) {
        AbstractDaoTemplate t = (AbstractDaoTemplate) jp.getTarget();
        String key = t.getEntityBeanType().getSimpleName() + ":";
        if (BeanUtils.isSimpleValueType(pk.getClass())) {
            key = key + pk;
        } else if (pk instanceof Map) {
            key = key + pk;
        } else {
            key = key + ToStringBuilder.reflectionToString(pk, ToStringStyle.SHORT_PREFIX_STYLE);
        }
        return key;
    }

    /**
     * 返回主键值
     *
     * @return 如果主键只有一个，则直接返回该值，如果有多个，则返回MAP对象
     */
    public Object getPrimaryKeyValue(RelationalPathBase<?> pathBase, Object domain) {
        Validate.notEmpty(pathBase.getPrimaryKey().getLocalColumns(),
                pathBase.getTableName() + "has 0 primaryKey");
        SqlParameterSource source = new BeanPropertySqlParameterSource(domain);
        if (pathBase.getPrimaryKey().getLocalColumns().size() == 1) {
            Path<?> path = pathBase.getPrimaryKey().getLocalColumns()
                    .get(0);
            return source.getValue(path.getMetadata().getName());
        }
        Map<String, Object> pkMap = new HashMap<String, Object>();
        for (Path<?> path : pathBase.getPrimaryKey().getLocalColumns()) {
            pkMap.put(path.getMetadata().getName(),
                    source.getValue(path.getMetadata().getName()));
        }
        return pkMap;
    }

    private CacheProvider getCacheWrapper(String cacheName) {
        return cacheProviderFactory.createCacheWrapper(cacheName);
    }

    private String getCacheName(JoinPoint jp) {
        AbstractDaoTemplate t = (AbstractDaoTemplate) jp.getTarget();
        return t.getEntityBeanType().getSimpleName() + "Cache";
    }
}

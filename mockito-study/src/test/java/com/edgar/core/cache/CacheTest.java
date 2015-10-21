package com.edgar.core.cache;

import com.edgar.core.repository.BaseDao;
import com.edgar.core.repository.QueryExample;
import com.edgar.module.sys.repository.domain.SysDict;
import net.sf.ehcache.CacheManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
@TransactionConfiguration(defaultRollback = true)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
public class CacheTest {
    @Autowired
    private BaseDao<String, SysDict> sysDictDao;

    @Autowired
    private CacheManager cacheManager;

    private CacheProvider<String, SysDict> cacheProvider;

    @Before
    public void setUp() {
        cacheProvider = new EhCacheProvider<String, SysDict>("SysDictCache", cacheManager);
    }

    @After
    public void tearDown() {
        cacheProvider.removeAll();
    }

    @Test
    public void testGetPut() {
        SysDict sysDict = cacheProvider.get("notExitst");
        Assert.assertNull(sysDict);
        sysDict = new SysDict();
        sysDict.setDictCode("notExitst");
        sysDict.setDictName("notExitst");
        cacheProvider.put("notExitst", sysDict);
        sysDict = cacheProvider.get("notExitst");
        Assert.assertEquals("notExitst", sysDict.getDictName());
        sysDict.setDictName("notExitst2");
        cacheProvider.put("notExitst", sysDict);
        sysDict = cacheProvider.get("notExitst");
        Assert.assertEquals("notExitst2", sysDict.getDictName());
        cacheProvider.remove("notExitst");
        sysDict = cacheProvider.get("notExitst");
        Assert.assertNull(sysDict);
    }

    @Test
    @Transactional
    public void testCache() {
        for (int i = 0; i < 10; i++) {
            SysDict sysDict = new SysDict();
            sysDict.setDictCode("000" + i);
            sysDict.setDictName("000" + i);
            sysDict.setParentCode("-1");
            sysDict.setSorted(9999);
            // sysDicts.add(sysDict);
            sysDictDao.insert(sysDict);
        }

        Assert.assertEquals(10, cacheManager.getCache("SysDictCache").getSize());
        SysDict sysDict = sysDictDao.get("0001");
        sysDictDao.update(sysDict);
        Assert.assertEquals(10, cacheManager.getCache("SysDictCache").getSize());
        sysDict = sysDictDao.get("0001");
        sysDictDao.updateWithVersion(sysDict);
        Assert.assertEquals(10, cacheManager.getCache("SysDictCache").getSize());
        sysDictDao.deleteByPk(sysDict.getDictCode());
        Assert.assertEquals(9, cacheManager.getCache("SysDictCache").getSize());
        sysDict = sysDictDao.get("0002");
        sysDictDao.deleteByPkAndVersion(sysDict.getDictCode(), sysDict.getUpdatedTime()
                .getTime());
        Assert.assertEquals(8, cacheManager.getCache("SysDictCache").getSize());
        QueryExample example = QueryExample.newInstance();
        example.equalsTo("dictCode", sysDict.getDictCode());
        sysDictDao.update(sysDict, example);
        Assert.assertEquals(0, cacheManager.getCache("SysDictCache").getSize());
    }

    @Test
    @Transactional
    public void testCache2() {
        List<SysDict> sysDicts = new ArrayList<SysDict>();
        for (int i = 0; i < 10; i++) {
            SysDict sysDict = new SysDict();
            sysDict.setDictCode("000" + i);
            sysDict.setDictName("000" + i);
            sysDict.setParentCode("-1");
            sysDict.setSorted(9999);
            sysDicts.add(sysDict);
        }
        sysDictDao.insert(sysDicts);

        Assert.assertEquals(10, cacheManager.getCache("SysDictCache").getSize());
        SysDict sysDict = sysDictDao.get("0001");
        sysDictDao.update(sysDict);
        Assert.assertEquals(10, cacheManager.getCache("SysDictCache").getSize());
        sysDict = sysDictDao.get("0001");
        sysDictDao.updateWithVersion(sysDict);
        Assert.assertEquals(10, cacheManager.getCache("SysDictCache").getSize());
        sysDictDao.deleteByPk(sysDict.getDictCode());
        Assert.assertEquals(9, cacheManager.getCache("SysDictCache").getSize());
        sysDict = sysDictDao.get("0002");
        sysDictDao.deleteByPkAndVersion(sysDict.getDictCode(), sysDict.getUpdatedTime()
                .getTime());
        Assert.assertEquals(8, cacheManager.getCache("SysDictCache").getSize());
        QueryExample example = QueryExample.newInstance();
        example.equalsTo("dictCode", sysDict.getDictCode());
        sysDictDao.delete(example);
        Assert.assertEquals(0, cacheManager.getCache("SysDictCache").getSize());
    }

    @Test
    @Transactional
    public void testCacheForDb() {
        for (int i = 0; i < 10; i++) {
            SysDict sysDict = new SysDict();
            sysDict.setDictCode("000" + i);
            sysDict.setDictName("000" + i);
            sysDict.setParentCode("-1");
            sysDict.setSorted(9999);
            // sysDicts.add(sysDict);
            sysDictDao.insert(sysDict);
        }
        Assert.assertEquals(10, cacheManager.getCache("SysDictCache").getSize());
        cacheProvider.remove("SysDict:0001");
        Assert.assertEquals(9, cacheManager.getCache("SysDictCache").getSize());
        sysDictDao.get("SysDict:0001");
        Assert.assertEquals(10, cacheManager.getCache("SysDictCache").getSize());
    }

    @Test
    @Transactional
    public void testCacheConcurrent() throws InterruptedException {
        cacheProvider.remove("SysDict:ROLE_CODE");
        ExecutorService exec = Executors.newCachedThreadPool();
        int num = 200;
        final CyclicBarrier barrier = new CyclicBarrier(num);
        final CountDownLatch latch = new CountDownLatch(num);
        for (int i = 0; i < num; i++) {
            exec.submit(new Runnable() {

                @Override
                public void run() {
                    try {
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    try {
                        sysDictDao.get("ROLE_CODE");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    latch.countDown();
                }
            });

        }
        latch.await();
    }
}
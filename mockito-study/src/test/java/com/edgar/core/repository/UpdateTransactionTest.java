package com.edgar.core.repository;

import com.edgar.core.cache.CacheProviderFactory;
import com.edgar.core.repository.transaction.Transaction;
import com.edgar.core.repository.transaction.TransactionConfig;
import com.edgar.core.repository.transaction.TransactionFactory;
import com.edgar.module.sys.repository.domain.TestTable;
import com.edgar.module.sys.repository.querydsl.QTestTable;
import com.mysema.query.sql.Configuration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
@TransactionConfiguration(defaultRollback = true)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
public class UpdateTransactionTest {

    @Autowired
    private BaseDao<String, TestTable> testTableDao;

    @Autowired
    private CacheProviderFactory cacheProviderFactory;

    @Autowired
    private DataSource dataSource;

    private Configuration configuration = ConfigurationFactory.createConfiguration(Constants.DEFAULT);

    private TransactionConfig config;

    private RowMapper<TestTable> rowMapper = BeanPropertyRowMapper.newInstance(TestTable.class);

    @Before
    public void setUp() {
        config = new TransactionConfig(dataSource, configuration, QTestTable.testTable);
        List<TestTable> testTables = new ArrayList<TestTable>();
        for (int i = 0; i < 10; i++) {
            TestTable testTable = new TestTable();
            testTable.setTestCode("000" + i);
            testTable.setDictName("000" + i);
            testTable.setParentCode("-1");
            testTable.setSorted(9999);
            testTables.add(testTable);
        }

        Transaction transaction = TransactionFactory.createDefaultBatchInsertTransaction(config, testTables);
        transaction.execute();
    }

    @After
    public void tearDown() {
        cacheProviderFactory.createCacheWrapper("TestTableCache").removeAll();
        cacheProviderFactory.createCacheWrapper("Test2TableCache").removeAll();
    }

    @Transactional
    @Test
    public void testUpdateByExample() {
        QueryExample example = QueryExample.newInstance();
        example.limit(1);
        List<TestTable> testTables = testTableDao.query(example);
        TestTable testTable = testTables.get(0);

        TestTable domain = new TestTable();
        domain.setDictName("zzz");
        ;
        domain.setTestCode("zzz");
        domain.setCreatedTime(testTable.getCreatedTime());
        domain.setUpdatedTime(testTable.getUpdatedTime());
        example.clear();
        example.equalsTo("testCode", testTable.getTestCode());
        example.equalsTo("updatedTime", testTable.getUpdatedTime());

        Transaction transaction = TransactionFactory.createDefaultUpdateTransaction(config, domain, example);
        Long result = transaction.execute();
        Assert.assertEquals(1l, result, 0);
        TestTable testTable2 = testTableDao.get(testTable.getTestCode());
        Assert.assertNotNull(testTable2.getParentCode());
        Assert.assertNotEquals(testTable2.getTestCode(), domain.getTestCode());
        Assert.assertEquals(testTable2.getDictName(), domain.getDictName());
        Assert.assertEquals(testTable2.getCreatedTime(), testTable.getCreatedTime());
        Assert.assertEquals(testTable2.getUpdatedTime(), testTable.getUpdatedTime());
    }

    @Test
    @Transactional
    public void testUpdateByExampleWithNullBindings() {
        QueryExample example = QueryExample.newInstance();
        example.limit(1);
        List<TestTable> testTables = testTableDao.query(example);
        TestTable testTable = testTables.get(0);

        TestTable domain = new TestTable();
        domain.setDictName("zzz");
        ;
        domain.setTestCode("zzz");
        domain.setParentCode("-1");
        example.clear();
        example.equalsTo("testCode", testTable.getTestCode());
        example.equalsTo("updatedTime", testTable.getUpdatedTime());
        Transaction transaction = TransactionFactory.createUpdateTransaction(config, domain, true, example);
        Long result = transaction.execute();
        Assert.assertEquals(1l, result, 0);
        testTable = testTableDao.get(testTable.getTestCode());
        Assert.assertNotNull(testTable.getParentCode());
        Assert.assertNull(testTable.getSorted());
        Assert.assertNotEquals(testTable.getTestCode(), domain.getTestCode());
        Assert.assertEquals(testTable.getDictName(), domain.getDictName());
    }

    @Transactional
    @Test
    public void testUpdateByExampleNotEquals() {

        QueryExample example = QueryExample.newInstance();
        example.limit(1);
        List<TestTable> testTables = testTableDao.query(example);
        TestTable testTable = testTables.get(0);

        TestTable domain = new TestTable();
        domain.setDictName("zzz");
        ;
        domain.setTestCode("zzz");
        domain.setParentCode("-1");
        example.clear();
        example.notEqualsTo("testCode", "0001");
        Transaction transaction = TransactionFactory.createUpdateTransaction(config, domain, true, example);
        Long result = transaction.execute();
        Assert.assertEquals(9l, result, 0);
        testTable = testTableDao.get(testTable.getTestCode());
        Assert.assertNotNull(testTable.getParentCode());
        Assert.assertNull(testTable.getSorted());
        Assert.assertNotEquals(testTable.getTestCode(), domain.getTestCode());
        Assert.assertEquals(testTable.getDictName(), domain.getDictName());
    }

    @Transactional
    @Test
    public void testUpdateByExampleWithIgnore() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        QueryExample example = QueryExample.newInstance();
        example.limit(1);
        List<TestTable> testTables = testTableDao.query(example);
        TestTable testTable = testTables.get(0);

        TestTable domain = new TestTable();
        domain.setDictName("zzz");
        ;
        domain.setTestCode("zzz");
        domain.setCreatedTime(testTable.getCreatedTime());
        domain.setUpdatedTime(testTable.getUpdatedTime());
        example.clear();
        example.equalsTo("testCode", testTable.getTestCode());
        example.equalsTo("updatedTime", testTable.getUpdatedTime());

        Transaction transaction = TransactionFactory.createDefaultUpdateTransaction(config, domain, example);
        Long result = transaction.execute();
        Assert.assertEquals(1l, result, 0);
        TestTable testTable2 = testTableDao.get(testTable.getTestCode());
        Assert.assertNotNull(testTable2.getParentCode());
        Assert.assertNotEquals(testTable2.getTestCode(), domain.getTestCode());
        Assert.assertEquals(testTable2.getDictName(), domain.getDictName());
        Assert.assertEquals(testTable2.getCreatedTime(), testTable.getCreatedTime());
        Assert.assertNotEquals(testTable2.getUpdatedTime(), testTable.getUpdatedTime());
    }

}

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
@TransactionConfiguration(defaultRollback = true)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
public class QueryTransactionTest {

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
    public void testQuery() {
        QueryExample example = QueryExample.newInstance();
        example = QueryExample.newInstance();
        Transaction transaction = TransactionFactory.createQueryTransaction(config, example, rowMapper);
        List<TestTable> testTables = transaction.execute();
        Assert.assertEquals(10, testTables.size());

        example.limit(10);
        transaction = TransactionFactory.createQueryTransaction(config, example, rowMapper);
        testTables = transaction.execute();
        Assert.assertEquals(10, testTables.size());
        example.equalsTo("testCode", "0001");
        transaction = TransactionFactory.createQueryTransaction(config, example, rowMapper);
        testTables = transaction.execute();
        Assert.assertEquals(1, testTables.size());

        example.clear();
        example.limit(10);
        example.greaterThan("test_code", "0001");
        example.asc("sorted");
        example.desc("testCode");
        transaction = TransactionFactory.createQueryTransaction(config, example, rowMapper);
        testTables = transaction.execute();
        Assert.assertEquals(8, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryReturnField() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.addField("testCode");

        Transaction transaction = TransactionFactory.createQueryTransaction(config, example, rowMapper);
        List<TestTable> testTables = transaction.execute();
        Assert.assertEquals(10, testTables.size());
        for (TestTable TestTable : testTables) {
            Assert.assertNotNull(TestTable.getTestCode());
            Assert.assertNull(TestTable.getDictName());
        }
        example.clear();
        example.limit(10);
        example.addField("testCodeew");
        transaction = TransactionFactory.createQueryTransaction(config, example, rowMapper);
        testTables = transaction.execute();
        Assert.assertEquals(10, testTables.size());
        for (TestTable TestTable : testTables) {
            Assert.assertNotNull(TestTable.getTestCode());
            Assert.assertNotNull(TestTable.getDictName());
        }
    }

    @Transactional
    @Test
    public void testQueryNotEquals() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.notEqualsTo("testCode", "0001");
        Transaction transaction = TransactionFactory.createQueryTransaction(config, example, rowMapper);
        List<TestTable> testTables = transaction.execute();
        Assert.assertEquals(9, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryGreatThan() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.greaterThan("testCode", "0001");
        Transaction transaction = TransactionFactory.createQueryTransaction(config, example, rowMapper);
        List<TestTable> testTables = transaction.execute();
        Assert.assertEquals(8, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryGreatThanAndEqulas() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.greaterThanOrEqualTo("testCode", "0001");
        Transaction transaction = TransactionFactory.createQueryTransaction(config, example, rowMapper);
        List<TestTable> testTables = transaction.execute();
        Assert.assertEquals(9, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryLessThan() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.lessThan("testCode", "0004");
        Transaction transaction = TransactionFactory.createQueryTransaction(config, example, rowMapper);
        List<TestTable> testTables = transaction.execute();
        Assert.assertEquals(4, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryLessThanAndEqulas() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.lessThanOrEqualTo("testCode", "0004");
        Transaction transaction = TransactionFactory.createQueryTransaction(config, example, rowMapper);
        List<TestTable> testTables = transaction.execute();
        Assert.assertEquals(5, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryLike() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.like("testCode", "000%");
        Transaction transaction = TransactionFactory.createQueryTransaction(config, example, rowMapper);
        List<TestTable> testTables = transaction.execute();
        Assert.assertEquals(10, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryNotLike() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.notLike("testCode", "0000001%");
        Transaction transaction = TransactionFactory.createQueryTransaction(config, example, rowMapper);
        List<TestTable> testTables = transaction.execute();
        Assert.assertEquals(10, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryBeginWith() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.beginWtih("testCode", "000");
        Transaction transaction = TransactionFactory.createQueryTransaction(config, example, rowMapper);
        List<TestTable> testTables = transaction.execute();
        Assert.assertEquals(10, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryNotBeginWith() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.notBeginWith("testCode", "000");
        Transaction transaction = TransactionFactory.createQueryTransaction(config, example, rowMapper);
        List<TestTable> testTables = transaction.execute();
        Assert.assertEquals(0, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryContain() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.contain("testCode", "00");
        Transaction transaction = TransactionFactory.createQueryTransaction(config, example, rowMapper);
        List<TestTable> testTables = transaction.execute();
        Assert.assertEquals(10, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryNotContain() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.notContain("testCode", "00");
        Transaction transaction = TransactionFactory.createQueryTransaction(config, example, rowMapper);
        List<TestTable> testTables = transaction.execute();
        Assert.assertEquals(0, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryIsNull() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.isNull("testCode");
        Transaction transaction = TransactionFactory.createQueryTransaction(config, example, rowMapper);
        List<TestTable> testTables = transaction.execute();
        Assert.assertEquals(0, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryIsNotNull() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.isNotNull("testCode");
        Transaction transaction = TransactionFactory.createQueryTransaction(config, example, rowMapper);
        List<TestTable> testTables = transaction.execute();
        Assert.assertEquals(10, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryBetween() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.between("testCode", "0001", "0002");
        Transaction transaction = TransactionFactory.createQueryTransaction(config, example, rowMapper);
        List<TestTable> testTables = transaction.execute();
        Assert.assertEquals(2, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryNotBetween() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.notBetween("testCode", "0001", "0002");
        Transaction transaction = TransactionFactory.createQueryTransaction(config, example, rowMapper);
        List<TestTable> testTables = transaction.execute();
        Assert.assertEquals(8, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryIn() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        List<Object> in = new ArrayList<Object>();
        in.add("0001");
        in.add("0002");
        example.in("testCode", in);
        Transaction transaction = TransactionFactory.createQueryTransaction(config, example, rowMapper);
        List<TestTable> testTables = transaction.execute();
        Assert.assertEquals(2, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryNotIn() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        List<Object> in = new ArrayList<Object>();
        in.add("0001");
        in.add("0002");
        example.notIn("testCode", in);
        Transaction transaction = TransactionFactory.createQueryTransaction(config, example, rowMapper);
        List<TestTable> testTables = transaction.execute();
        Assert.assertEquals(8, testTables.size());
    }

    @Transactional
    @Test
    public void testQuerySingleColumn() {
        QueryExample example = QueryExample.newInstance();
        example.equalsTo("testCode", "0001");
        example.addField("testCode");

        Transaction transaction = TransactionFactory.createQueryTransaction(config, example, rowMapper);

        List<String> testTables = transaction.execute();
        Assert.assertEquals(1, testTables.size());
        example.addField("dictName");
        transaction = TransactionFactory.createQueryTransaction(config, example, rowMapper);
        try {
            testTables = transaction.execute();
            ;
            Assert.assertEquals(1, testTables.size());
        } catch (Exception e) {

        }
    }
}

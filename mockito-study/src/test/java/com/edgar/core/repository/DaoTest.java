package com.edgar.core.repository;

import com.edgar.core.cache.CacheProviderFactory;
import com.edgar.module.sys.repository.domain.TestTable;
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

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
@TransactionConfiguration(defaultRollback = true)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
public class DaoTest {

    @Autowired
    private BaseDao<String, TestTable> testTableDao;

    @Autowired
    private CacheProviderFactory cacheProviderFactory;

    @Autowired
    private DataSource dataSource;

    @Before
    public void setUp() {
        List<TestTable> testTables = new ArrayList<TestTable>();
        for (int i = 0; i < 10; i++) {
            TestTable testTable = new TestTable();
            testTable.setTestCode("000" + i);
            testTable.setDictName("000" + i);
            testTable.setParentCode("-1");
            testTable.setSorted(9999);
            testTables.add(testTable);
        }
        testTableDao.insert(testTables);
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
        List<TestTable> testTables = testTableDao.query(example);
        Assert.assertEquals(10, testTables.size());
        example.limit(10);
        testTables = testTableDao.query(example);
        Assert.assertEquals(10, testTables.size());
        example.equalsTo("testCode", "0001");
        testTables = testTableDao.query(example);
        Assert.assertEquals(1, testTables.size());
        example.clear();
        example.limit(10);
        example.greaterThan("test_code", "0001");
        example.asc("sorted");
        example.desc("testCode");
        testTables = testTableDao.query(example);
        Assert.assertEquals(8, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryReturnField() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.addField("testCode");
        List<TestTable> testTables = testTableDao.query(example);
        Assert.assertEquals(10, testTables.size());
        for (TestTable TestTable : testTables) {
            Assert.assertNotNull(TestTable.getTestCode());
            Assert.assertNull(TestTable.getDictName());
        }
        example.clear();
        example.limit(10);
        example.addField("testCodeew");
        testTables = testTableDao.query(example);
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
        List<TestTable> testTables = testTableDao.query(example);
        Assert.assertEquals(9, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryGreatThan() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.greaterThan("testCode", "0001");
        List<TestTable> testTables = testTableDao.query(example);
        Assert.assertEquals(8, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryGreatThanAndEqulas() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.greaterThanOrEqualTo("testCode", "0001");
        List<TestTable> testTables = testTableDao.query(example);
        Assert.assertEquals(9, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryLessThan() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.lessThan("testCode", "0004");
        List<TestTable> testTables = testTableDao.query(example);
        Assert.assertEquals(4, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryLessThanAndEqulas() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.lessThanOrEqualTo("testCode", "0004");
        List<TestTable> testTables = testTableDao.query(example);
        Assert.assertEquals(5, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryLike() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.like("testCode", "000%");
        List<TestTable> testTables = testTableDao.query(example);
        Assert.assertEquals(10, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryNotLike() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.notLike("testCode", "0000001%");
        List<TestTable> testTables = testTableDao.query(example);
        Assert.assertEquals(10, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryBeginWith() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.beginWtih("testCode", "000");
        List<TestTable> testTables = testTableDao.query(example);
        Assert.assertEquals(10, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryNotBeginWith() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.notBeginWith("testCode", "000");
        List<TestTable> testTables = testTableDao.query(example);
        Assert.assertEquals(0, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryContain() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.contain("testCode", "00");
        List<TestTable> testTables = testTableDao.query(example);
        Assert.assertEquals(10, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryNotContain() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.notContain("testCode", "00");
        List<TestTable> testTables = testTableDao.query(example);
        Assert.assertEquals(0, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryIsNull() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.isNull("testCode");
        List<TestTable> testTables = testTableDao.query(example);
        Assert.assertEquals(0, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryIsNotNull() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.isNotNull("testCode");
        List<TestTable> testTables = testTableDao.query(example);
        Assert.assertEquals(10, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryBetween() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.between("testCode", "0001", "0002");
        List<TestTable> testTables = testTableDao.query(example);
        Assert.assertEquals(2, testTables.size());
    }

    @Transactional
    @Test
    public void testQueryNotBetween() {
        QueryExample example = QueryExample.newInstance();
        example.limit(10);
        example.notBetween("testCode", "0001", "0002");
        List<TestTable> testTables = testTableDao.query(example);
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
        List<TestTable> testTables = testTableDao.query(example);
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
        List<TestTable> testTables = testTableDao.query(example);
        Assert.assertEquals(8, testTables.size());
    }

    @Transactional
    @Test
    public void testQuerySingleColumn() {
        QueryExample example = QueryExample.newInstance();
        example.equalsTo("testCode", "0001");
        example.addField("testCode");
        List<String> testTables = testTableDao.querySingleColumn(example, String.class);
        Assert.assertEquals(1, testTables.size());
        example.addField("dictName");
        try {
            testTables = testTableDao.querySingleColumn(example, String.class);
            Assert.assertEquals(1, testTables.size());
        } catch (Exception e) {

        }
    }

    @Transactional
    @Test
    public void testPage() {
        QueryExample example = QueryExample.newInstance();
        Pagination<TestTable> testTables = testTableDao.pagination(example, 1, 10);
        // Assert.assertTrue(testTables.getTotalRecords() <= example
        // .getMaxNumOfRecords());
        Assert.assertEquals(1, testTables.getPage());
        Assert.assertEquals((testTables.getTotalRecords() + testTables.getPageSize() - 1)
                / testTables.getPageSize(), testTables.getTotalPages());
        Assert.assertEquals(1, testTables.getPrevPage());
        Assert.assertEquals(1, testTables.getNextPage());

        testTables = testTableDao.pagination(example, 2, 10);
        Assert.assertEquals(10, testTables.getRecords().size());
        // Assert.assertTrue(testTables.getTotalRecords() <= example
        // .getMaxNumOfRecords());
        Assert.assertEquals(1, testTables.getPage());
        Assert.assertEquals((testTables.getTotalRecords() + testTables.getPageSize() - 1)
                / testTables.getPageSize(), testTables.getTotalPages());
        Assert.assertEquals(1, testTables.getPrevPage());
        Assert.assertEquals(1, testTables.getNextPage());

        testTables = testTableDao.pagination(example, 3, 5);
        Assert.assertEquals(2, testTables.getPage());
        Assert.assertEquals((testTables.getTotalRecords() + testTables.getPageSize() - 1)
                / testTables.getPageSize(), testTables.getTotalPages());
        Assert.assertEquals(1, testTables.getPrevPage());
        Assert.assertEquals(2, testTables.getNextPage());
    }

    @Transactional
    @Test(expected = IllegalArgumentException.class)
    public void testGreatThanMaxPage() {
        QueryExample example = QueryExample.newInstance();
        example.setMaxNumOfRecords(1000);
        testTableDao.pagination(example, 200, 10);
    }

    @Transactional
    @Test
    public void testInsert() {
        QueryExample example = QueryExample.newInstance();
        // example.equalsTo("testCode", "0001");
        List<TestTable> testTables = testTableDao.query(example);
        int count = testTables.size();
        TestTable TestTable = new TestTable();
        TestTable.setTestCode("9999");
        TestTable.setDictName("9999");
        TestTable.setParentCode("-1");
        TestTable.setSorted(9999);
        testTableDao.insert(TestTable);
        // example.equalsTo("testCode", "9999");
        testTables = testTableDao.query(example);
        Assert.assertEquals(count + 1, testTables.size());
    }

    @Transactional
    @Test
    public void testinsert() {
        List<TestTable> testTables = new ArrayList<TestTable>();
        for (int i = 9999; i < 11000; i++) {
            TestTable TestTable = new TestTable();
            TestTable.setTestCode("000" + i);
            TestTable.setDictName("000" + i);
            TestTable.setParentCode("000");
            TestTable.setSorted(9999);
            testTables.add(TestTable);
        }
        testTableDao.insert(testTables);
    }

    @Transactional
    @Test
    public void testUpdateByExample() {
        QueryExample example = QueryExample.newInstance();
        example.limit(1);
        List<TestTable> testTables = testTableDao.query(example);
        TestTable TestTable = testTables.get(0);
        TestTable.setParentCode("1234");
        example.clear();
        example.equalsTo("testCode", TestTable.getTestCode());
        Assert.assertEquals(1l, testTableDao.update(TestTable, example), 0);
        TestTable = testTableDao.get(TestTable.getTestCode());
        Assert.assertNotNull(TestTable.getParentCode());
        Assert.assertEquals(TestTable.getParentCode(), "1234");
    }

    @Transactional
    @Test
    public void testUpdateByBeginWithExample() {
        QueryExample example = QueryExample.newInstance();
        example.beginWtih("testCode", "00");
        List<TestTable> testTables = testTableDao.query(example);
        TestTable TestTable = new TestTable();
        TestTable.setParentCode("1234");
        Assert.assertEquals(testTables.size(), testTableDao.update(TestTable, example), 0);
        testTables = testTableDao.query(example);
        for (TestTable dict : testTables) {
            Assert.assertEquals("1234", dict.getParentCode());
        }
    }

    @Transactional
    @Test
    public void testNoUpdateByExample() {
        QueryExample example = QueryExample.newInstance();
        example.beginWtih("testCode", "00000000000");
        TestTable TestTable = new TestTable();
        TestTable.setParentCode("1234");
        Assert.assertEquals(0, testTableDao.update(TestTable, example), 0);
    }

    @Transactional
    @Test
    public void testUpdateByPrimaryKey() {
        QueryExample example = QueryExample.newInstance();
        example.limit(1);
        List<TestTable> testTables = testTableDao.query(example);
        TestTable TestTable = testTables.get(0);
        TestTable.setParentCode("-1");
        Assert.assertEquals(1, testTableDao.update(TestTable), 0);
        TestTable newTestTable = testTableDao.get(TestTable.getTestCode());
        Assert.assertNotNull(newTestTable.getTestCode());
        // Assert.assertNotEquals(newTestTable.getUpdatedTime(),
        // TestTable.getUpdatedTime());
    }

    @Transactional
    @Test(expected = StaleObjectStateException.class)
    public void testUpdateByVersion() {
        QueryExample example = QueryExample.newInstance();
        example.limit(1);
        List<TestTable> testTables = testTableDao.query(example);
        TestTable TestTable = testTables.get(0);
        TestTable.setParentCode("-1");
        TestTable.setUpdatedTime(new Timestamp(1000));
        testTableDao.updateWithVersion(TestTable);
    }

    @Transactional
    @Test
    public void testGet() {
        QueryExample example = QueryExample.newInstance();
        example.limit(1);
        List<TestTable> testTables = testTableDao.query(example);
        TestTable TestTable = testTables.get(0);
        TestTable getTestTable = testTableDao.get(TestTable.getTestCode());
        Assert.assertEquals(TestTable.getTestCode(), getTestTable.getTestCode());
        getTestTable = testTableDao.get("11");
        Assert.assertNull(getTestTable);
    }

    @Transactional
    @Test
    public void testGetField() {
        QueryExample example = QueryExample.newInstance();
        example.limit(1);
        List<TestTable> testTables = testTableDao.query(example);
        TestTable TestTable = testTables.get(0);
        List<String> fields = new ArrayList<String>();
        fields.add("testCode");
        // fields.add("dictName");
        TestTable getTestTable = testTableDao.get(TestTable.getTestCode(), fields);
        Assert.assertEquals(TestTable.getTestCode(), getTestTable.getTestCode());
        Assert.assertNotNull(getTestTable.getTestCode());
        Assert.assertNull(getTestTable.getDictName());
    }

    @Transactional
    @Test
    public void testDeleteByPk() {
        QueryExample example = QueryExample.newInstance();
        example.limit(1);
        List<TestTable> testTables = testTableDao.query(example);
        TestTable TestTable = testTables.get(0);
        long result = testTableDao.deleteByPk(TestTable.getTestCode());
        Assert.assertEquals(1, result);
        TestTable getTestTable = testTableDao.get(TestTable.getTestCode());
        Assert.assertNull(getTestTable);
    }

    @Transactional
    @Test(expected = StaleObjectStateException.class)
    public void testDeleteByPKAndVersion() {
        QueryExample example = QueryExample.newInstance();
        example.limit(1);
        List<TestTable> testTables = testTableDao.query(example);
        TestTable TestTable = testTables.get(0);
        long result = testTableDao.deleteByPkAndVersion(TestTable.getTestCode(), TestTable
                .getUpdatedTime().getTime());
        Assert.assertEquals(1, result);
        TestTable getTestTable = testTableDao.get(TestTable.getTestCode());
        Assert.assertNull(getTestTable);

        example.clear();
        example.limit(1);
        testTables = testTableDao.query(example);
        TestTable = testTables.get(0);
        result = testTableDao.deleteByPkAndVersion(TestTable.getTestCode(), 1L);
        Assert.assertEquals(0, result);
    }

    @Transactional
    @Test
    public void testDeleteByExample() {
        QueryExample example = QueryExample.newInstance();
        example.endWtih("testCode", "5");
        List<TestTable> testTables = testTableDao.query(example);
        int expected = testTables.size();
        long result = testTableDao.delete(example);
        Assert.assertEquals(expected, result);
    }

}

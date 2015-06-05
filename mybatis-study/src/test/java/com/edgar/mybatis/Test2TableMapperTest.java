package com.edgar.mybatis;

import com.edgar.domain.CompanyConfig;
import com.edgar.domain.Test2Table;
import com.edgar.mapper.CompanyConfigMapper;
import com.edgar.mapper.Test2TableMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/6/3.
 */
public class Test2TableMapperTest {

    SqlSession session;
    Test2TableMapper mapper;

    @Before
    public void setUp() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        session = sqlSessionFactory.openSession();
        mapper = session.getMapper(Test2TableMapper.class);

        mapper.createTable();

        for (int i = 0; i < 10; i++) {
            Test2Table test2Table = new Test2Table();
            test2Table.setTestId("000" + i);
            test2Table.setTestCode2("000" + i);
            test2Table.setDictName("000" + i);
            mapper.insert(test2Table);

        }
    }

    @After
    public void tearDown() {
        mapper.dropTable();
        session.close();
    }

    @Test
    public void testSelectByPrimaryKey() throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("test_id", "0001");
        map.put("test_code2", "0001");
        Test2Table test2Table = mapper.selectByPrimaryKey(map);
        Assert.assertEquals("0001", test2Table.getTestCode2());
        map.put("test_code2", "0002");
        test2Table = mapper.selectByPrimaryKey(map);
        Assert.assertNull(test2Table);
    }

    @Test
    public void testInsert() throws IOException {
        int count = mapper.count();
        Test2Table test2Table = new Test2Table();
        test2Table.setTestId("0011");
        test2Table.setTestCode2("0011");
        test2Table.setDictName("0011");
        int result = mapper.insert(test2Table);
        Assert.assertEquals(1, result);
        Assert.assertEquals(count + 1, mapper.count());
    }

    @Test
    public void testUpdateByPrimaryKey() throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("test_id", "0001");
        map.put("test_code2", "0001");
        Test2Table test2Table = mapper.selectByPrimaryKey(map);
        Assert.assertEquals("0001", test2Table.getTestCode2());
        test2Table.setDictName("new value");
        int result = mapper.updateByPrimaryKey(test2Table);
        Assert.assertEquals(1, result);
        test2Table = mapper.selectByPrimaryKey(map);
        Assert.assertEquals("new value", test2Table.getDictName());
        test2Table.setTestCode2("2343");
        result = mapper.updateByPrimaryKey(test2Table);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testDeleteByPrimaryKey() throws IOException {
        int count = mapper.count();
        Map<String, Object> map = new HashMap<>();
        map.put("test_id", "0001");
        map.put("test_code2", "0001");
        int result = mapper.deleteByPrimaryKey(map);
        Assert.assertEquals(1, result);
        Assert.assertEquals(count - 1, mapper.count());
        map.put("test_code2", "test_code2");
        result = mapper.deleteByPrimaryKey(map);
        Assert.assertEquals(0, result);
        Assert.assertEquals(count - 1, mapper.count());
    }

}

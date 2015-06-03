package com.edgar.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2015/6/3.
 */
public class CompanyConfigMapperTest {

    @Test
    public void test() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CompanyConfigMapper mapper = session.getMapper(CompanyConfigMapper.class);
            CompanyConfig config = mapper.selectByPrimaryKey(1);
            System.out.println(config.getConfigKey());
        } finally {
            session.close();
        }
    }
}

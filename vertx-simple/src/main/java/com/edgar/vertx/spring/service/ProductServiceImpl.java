package com.edgar.vertx.spring.service;

import com.edgar.vertx.spring.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Administrator on 2015/9/8.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private DataSource dataSource;

    @Override
    public List<Product> getAllProducts() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.query("select * from product", BeanPropertyRowMapper.newInstance(Product.class));
    }
}

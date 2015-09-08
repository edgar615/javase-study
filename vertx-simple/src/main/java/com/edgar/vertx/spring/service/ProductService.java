package com.edgar.vertx.spring.service;

import com.edgar.vertx.spring.entity.Product;

import java.util.List;

/**
 * Created by Administrator on 2015/9/8.
 */
public interface ProductService {
    List<Product> getAllProducts();
}

package com.edgar.vertx.springext;

import com.edgar.vertx.spring.context.ExampleSpringConfiguration;
import com.edgar.vertx.spring.verticle.ServerVerticle;
import com.edgar.vertx.spring.verticle.SpringDemoVerticle;
import io.vertx.core.Vertx;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by Administrator on 2015/9/8.
 */
public class SpringExampleRunner {
    public static void main( String[] args ) {
        final Vertx vertx = Vertx.vertx();
//        vertx.deployVerticle(new MySpringVerticle());
        vertx.deployVerticle("java-spring:com.edgar.vertx.springext.MySpringVerticle");
    }
}

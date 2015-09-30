package com.edgar.vertx.springext;

import io.vertx.core.AbstractVerticle;
import org.jacpfx.vertx.spring.SpringVerticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@SpringVerticle(springConfig=ExampleSpringConfiguration.class)
public class MySpringVerticle extends AbstractVerticle {

    @Resource
    private SayHelloBean bean;

    @Override
    public void start() {
        bean.sayHello();
    }

    @Override
    public void stop() {
    }
}
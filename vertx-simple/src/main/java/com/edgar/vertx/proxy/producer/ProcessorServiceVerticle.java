package com.edgar.vertx.proxy.producer;

import io.vertx.core.AbstractVerticle;
import io.vertx.serviceproxy.ProxyHelper;

public class ProcessorServiceVerticle extends AbstractVerticle {
    ProcessorService service;

    @Override
    public void start() throws Exception {
// Create the client object
        service = new ProcessorServiceImpl();
// Register the handler
        ProxyHelper.registerService(ProcessorService.class, vertx, service, "vertx.processor");
    }
}
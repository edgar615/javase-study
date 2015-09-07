package com.edgar.vertx.simple;

import io.vertx.core.Vertx;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/8/25.
 */
public class BlockServer {
    public static void main(String[] args) {
        // Create an HTTP server which simply returns "Hello World!" to each request.
        Vertx.vertx().executeBlocking(future -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            future.complete("hello");
        }, res -> {
            System.out.println("The result is: " + res.result());
        });

    }
}
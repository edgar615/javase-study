package com.edgar.vertx.simple;

import io.vertx.core.Vertx;

/**
 * Created by Administrator on 2015/8/25.
 */
public class PeriodServer {
    public static void main(String[] args) {
        // Create an HTTP server which simply returns "Hello World!" to each request.
        Vertx.vertx().setPeriodic(1000, id -> {
            System.out.println("timer fired!");
        });

    }
}
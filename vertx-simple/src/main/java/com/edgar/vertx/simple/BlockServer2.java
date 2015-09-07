package com.edgar.vertx.simple;

import io.vertx.core.Vertx;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/8/25.
 */
public class BlockServer2 {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.createHttpServer().requestHandler(request -> {
// Let's say we have to call a blocking API (e.g. JDBC) to execute a query for each
// request. We can't do this directly or it will block the event loop
// But you can do this using executeBlocking:
            vertx.<String>executeBlocking(future -> {
// Do the blocking operation in here
// Imagine this was a call to a blocking API to get the result
                try {
                    Thread.sleep(5000);
                } catch (Exception ignore) {
                }
                String result = "armadillos!";
                future.complete(result);
            }, res -> {
                if (res.succeeded()) {
                    request.response().putHeader("content-type", "text/plain").end(res.result());
                } else {
                    res.cause().printStackTrace();
                }
            });
        }).listen(8080);

    }
}
package com.edgar.vertx.simple;

import io.vertx.core.Vertx;

public class HelloWorldEmbedded {
    public static void main(String[] args) {
    // Create an HTTP server which simply returns "Hello World!" to each request.
        Vertx.vertx().createHttpServer().requestHandler(req -> req.response().end("Hello World!")).listen(8080);

        //Specifying options when creating a Vertx object
//        Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(40));
    }
}

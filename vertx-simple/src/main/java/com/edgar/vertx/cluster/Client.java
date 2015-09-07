package com.edgar.vertx.cluster;

import com.edgar.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;

public class Client extends AbstractVerticle {
    public static void main(String[] args) {
        Runner.runExample(Client.class);
    }

    @Override
    public void start() throws Exception {
        vertx.setPeriodic(1000, l -> {
           vertx.createHttpClient().getNow(5702, "localhost", "/", resp -> {
               resp.bodyHandler(body -> {
                   System.out.println(body.toString("ISO-8859-1"));
               });
           }) ;
        });
    }
}
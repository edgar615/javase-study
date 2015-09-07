package com.edgar.vertx.http.simple;

import com.edgar.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;

/**
 * Created by Administrator on 2015/9/1.
 */
public class Client extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(Client.class);
    }

    @Override
    public void start() throws Exception {
        vertx.createHttpClient().getNow(8080, "localhost", "/", response -> {
            System.out.println("Got response " + response.statusCode());
            response.bodyHandler(body -> System.out.println("Got data " + body.toString("utf-8")));
        });
    }
}

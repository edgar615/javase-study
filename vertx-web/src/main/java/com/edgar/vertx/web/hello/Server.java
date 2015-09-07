package com.edgar.vertx.web.hello;

import com.edgar.vertx.web.util.Runner;
import io.vertx.core.AbstractVerticle;

/**
 * Created by Administrator on 2015/9/6.
 */
public class Server extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(Server.class);
    }

    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(req -> {
            req.response().putHeader("content-type", "text/html").end("Hello world");
        }).listen(8080);
    }
}

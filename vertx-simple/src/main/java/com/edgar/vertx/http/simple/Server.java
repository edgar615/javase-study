package com.edgar.vertx.http.simple;

import com.edgar.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;

/**
 * Created by Administrator on 2015/9/1.
 */
public class Server extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(Server.class);
    }

    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(
                request -> request.response()
                        .putHeader("content-type", "text/html")
                        .end("<html><body><h1>Hello from vert.x!</h1></body></html>"))
                .listen(8080);
    }
}

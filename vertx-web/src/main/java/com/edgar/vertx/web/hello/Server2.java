package com.edgar.vertx.web.hello;

import com.edgar.vertx.web.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

/**
 * Created by Administrator on 2015/9/6.
 */
public class Server2 extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(Server2.class);
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        router.route().handler(routerContext -> {
            routerContext.response().putHeader("content-type", "text/html").end("Hello World from Vert.x-Web!");
        });
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
//        vertx.createHttpServer().requestHandler(req -> {
//            router.accept(req);
//        }).listen(8080);
    }
}

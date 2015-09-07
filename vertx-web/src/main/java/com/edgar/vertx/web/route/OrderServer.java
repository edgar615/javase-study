package com.edgar.vertx.web.route;

import com.edgar.vertx.web.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

/**
 * Created by Administrator on 2015/9/6.
 */
public class OrderServer extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(OrderServer.class);
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        router.route("/some/path").handler(routerContext -> {
            routerContext.response()
                    .setChunked(true)
                    .write("route1\n");
            routerContext.next();
        });
        router.route("/some/path").handler(routerContext -> {
            routerContext.response().write("route2\n");
            routerContext.next();
        });
        router.route("/some/path").handler(routerContext -> {
            routerContext.response().write("route3");
            routerContext.response().end();
        });
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
}

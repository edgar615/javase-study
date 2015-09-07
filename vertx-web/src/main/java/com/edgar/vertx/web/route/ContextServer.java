package com.edgar.vertx.web.route;

import com.edgar.vertx.web.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

/**
 * Created by Administrator on 2015/9/6.
 */
public class ContextServer extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(ContextServer.class);
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        router.route("/some/path").handler(routerContext -> {
           routerContext.put("foo", "bar").next();
        });
        router.route("/some/path").handler(routerContext -> {
            String foo = routerContext.get("foo");
            routerContext.response().end(foo);
        });
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
}

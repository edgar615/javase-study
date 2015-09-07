package com.edgar.vertx.web.route;

import com.edgar.vertx.web.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

/**
 * Created by Administrator on 2015/9/6.
 */
public class PathParamServer extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(PathParamServer.class);
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        router.route("/user/:id").handler(routerContext -> {
            String id = routerContext.request().getParam("id");
            routerContext.response().putHeader("content-type", "text/html").end(id);
        });
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
}

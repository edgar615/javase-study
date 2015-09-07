package com.edgar.vertx.web.route;

import com.edgar.vertx.web.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

/**
 * Created by Administrator on 2015/9/6.
 */
public class PathServer extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(PathServer.class);
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
//        Route route = router.route().path("/some/path/");
        router.route("/some/path/*").handler(routerContext -> {
            routerContext.response().putHeader("content-type", "text/html").end("Hello World from Vert.x-Web!");
        });
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
}

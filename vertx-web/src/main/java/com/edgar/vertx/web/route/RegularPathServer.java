package com.edgar.vertx.web.route;

import com.edgar.vertx.web.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

/**
 * Created by Administrator on 2015/9/6.
 */
public class RegularPathServer extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(RegularPathServer.class);
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
//        Route route = router.route().pathRegex(".*foo");
        router.routeWithRegex(".*foo").handler(routerContext -> {
            routerContext.response().putHeader("content-type", "text/html").end("Hello World from Vert.x-Web!");
        });
        // This handler will be called for:

        // /some/path/foo
        // /foo
        // /foo/bar/wibble/foo

        // But not:
        // /bar/wibble
        // /foo/bar

        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
}

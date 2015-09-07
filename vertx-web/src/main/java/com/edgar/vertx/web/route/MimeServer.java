package com.edgar.vertx.web.route;

import com.edgar.vertx.web.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

/**
 * Created by Administrator on 2015/9/6.
 */
public class MimeServer extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(MimeServer.class);
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
//        Route route = router.route().path("/some/path/");
        router.route().consumes("text/html").consumes("text/plain").handler(routerContext -> {
            routerContext.response().putHeader("content-type", "text/html").end("Hello World from Vert.x-Web!");
        });
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);

//        router.route().consumes("text/*").handler(routingContext -> {
//            // This handler will be called for any request with top level type `text`
//            // e.g. content-type header set to `text/html` or `text/plain` will both match
//        });
//        router.route().consumes("*/json").handler(routingContext -> {
//            // This handler will be called for any request with sub-type json
//            // e.g. content-type header set to `text/json` or `application/json` will both match
//        });
    }
}

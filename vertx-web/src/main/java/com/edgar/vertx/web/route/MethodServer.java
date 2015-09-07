package com.edgar.vertx.web.route;

import com.edgar.vertx.web.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

/**
 * Created by Administrator on 2015/9/6.
 */
public class MethodServer extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(MethodServer.class);
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        router.route(HttpMethod.GET, "/some/path/*").handler(routerContext -> {
            routerContext.response().putHeader("content-type", "text/html").end("Hello World from Vert.x-Web!");
        });
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);

//        Route route = router.route().method(HttpMethod.POST);
//        route.handler(routingContext -> {
//            // This handler will be called for any POST request
//        });

//        router.get().handler(routingContext -> {
//            // Will be called for any GET request
//        });
//        router.get("/some/path/").handler(routingContext -> {
//            // Will be called for any GET request to a path
//            // starting with /some/path
//        });
//        router.getWithRegex(".*foo").handler(routingContext -> {
//            // Will be called for any GET request to a path
//            // ending with `foo`
//        });

        //If you want to specify a route will match for more than HTTP method you can call method multiple times:
//        Route route = router.route().method(HttpMethod.POST).method(HttpMethod.PUT);
    }
}

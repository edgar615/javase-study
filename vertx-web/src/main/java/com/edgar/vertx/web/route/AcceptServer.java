package com.edgar.vertx.web.route;

import com.edgar.vertx.web.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

/**
 * Created by Administrator on 2015/9/6.
 */
public class AcceptServer extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(AcceptServer.class);
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
//        Route route = router.route().path("/some/path/");
        router.route().produces("text/html").handler(routerContext -> {
            routerContext.response().putHeader("content-type", "text/html").end("Hello World from Vert.x-Web!");
        });
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);

//        Accept: text/plain
//        Accept: text/plain, text/html
//        Accept: text/plain; q=0.9, text/html

//        router.route().produces("application/json").handler(routingContext -> {
//            HttpServerResponse response = routingContext.response();
//            response.putHeader("content-type", "application/json");
//            response.write(someJSON).end();
//        });

//        Accept: application/json
//        Accept: application/*
//        Accept: application/json, text/html
//        Accept: application/json;q=0.7, text/html;q=0.8, text/plain

        //You can also mark your route as producing more than one MIME type. If this is the case, then you use getAcceptableContentType to find out the actual MIME type that was accepted.
//        router.route().produces("application/json").produces("text/html").handler(routingContext -> {
//            HttpServerResponse response = routingContext.response();
//            // Get the actual MIME type acceptable
//            String acceptableContentType = routingContext.getAcceptableContentType();
//            response.putHeader("content-type", acceptableContentType);
//            response.write(whatever).end();
//        });

        //Combining routing criteria
//        Route route = router.route(HttpMethod.PUT, "myapi/orders")
//                .consumes("application/json")
//                .produces("application/json");
//        route.handler(routingContext -> {
//            // This would be match for any PUT method to paths starting with "myapi/orders" with a
//            // content-type of "application/json"
//            // and an accept header matching "application/json"
//        });
    }
}

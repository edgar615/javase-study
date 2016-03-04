package com.edgar.vertx.web.handler;

import com.edgar.vertx.web.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

/**
 * Created by Administrator on 2015/9/6.
 */
public class ErrorHandleServer extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(ErrorHandleServer.class);
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        Route route1 = router.get("/somepath/path1");
        route1.handler(routingContext -> {
            // Let's say this throws a RuntimeException
            throw new RuntimeException("something happened!");
        });

        Route route2 = router.get("/somepath/path2");
        route2.handler(routingContext -> {
            // This one deliberately fails the request passing in the status code
            // E.g. 403 - Forbidden
            routingContext.fail(403);
        });

        // Define a failure handler
        // This will get called for any failures in the above handlers
        Route route3 = router.get("/*");

        route3.failureHandler(failureRoutingContext -> {

            int statusCode = failureRoutingContext.statusCode();
            // Status code will be 500 for the RuntimeException or 403 for the other failure
            HttpServerResponse response = failureRoutingContext.response();
            if (statusCode > 0) {
                response.setStatusCode(statusCode).end("Sorry! Not today");
            }
            response.setStatusCode(500).end("Sorry! Not today");

        });

        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
}

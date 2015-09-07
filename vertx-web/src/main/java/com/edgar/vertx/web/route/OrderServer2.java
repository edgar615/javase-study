package com.edgar.vertx.web.route;

import com.edgar.vertx.web.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

/**
 * Created by Administrator on 2015/9/6.
 */
public class OrderServer2 extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(OrderServer2.class);
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);

        //Routes are assigned an order at creation time corresponding to the order in which they were added to the router, with the first route numbered 0, the second route numbered 1, and so on.
        //By specifying an order for the route you can override the default ordering. Order can also be negative, e.g. if you want to ensure a route is evaluated before route number 0.

        Route route1 = router.route("/some/path").handler(routerContext -> {
            routerContext.response().write("route1\n");
            routerContext.next();
        });
        Route route2 = router.route("/some/path").handler(routerContext -> {
            routerContext.response()
                    .setChunked(true)
                    .write("route2\n");
            routerContext.next();
        });
        Route route3 = router.route("/some/path").handler(routerContext -> {
            routerContext.response().write("route3");
            routerContext.response().end();
        });
        route2.order(-1);
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
}

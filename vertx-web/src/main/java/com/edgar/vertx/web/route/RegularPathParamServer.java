package com.edgar.vertx.web.route;

import com.edgar.vertx.web.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

/**
 * Created by Administrator on 2015/9/6.
 */
public class RegularPathParamServer extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(RegularPathParamServer.class);
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        router.routeWithRegex(".*foo").pathRegex("\\/([^\\/]+)\\/([^\\/]+)").handler(routerContext -> {
            String param0 = routerContext.request().getParam("param0");
            String param1 = routerContext.request().getParam("param1");
            routerContext.response().putHeader("content-type", "text/html").end(param0 + "\n" + param1);
        });

        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
}

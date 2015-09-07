package com.edgar.vertx.web.resource;

import com.edgar.vertx.web.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * Created by Administrator on 2015/9/7.
 */
public class ResourceServer extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(ResourceServer.class);
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        Route route = router.route("/static/*").handler(StaticHandler.create());
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
}

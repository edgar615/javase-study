package com.edgar.vertx.web.route;

import com.edgar.vertx.web.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

/**
 * Created by Administrator on 2015/9/6.
 */
public class SubRouterServer extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(SubRouterServer.class);
    }

    @Override
    public void start() throws Exception {
        Router restAPI = Router.router(vertx);

        restAPI.get("/products/:productID").handler(rc -> {

            // TODO Handle the lookup of the product....
            rc.response().end("get");

        });

        restAPI.put("/products/:productID").handler(rc -> {

            // TODO Add a new product...
            rc.response().end("put");

        });

        restAPI.delete("/products/:productID").handler(rc -> {

            // TODO delete the product...
            rc.response().end("delete");

        });

        Router mainRouter = Router.router(vertx);

// Handle static resources
        mainRouter.route("/static/*").handler(rc -> {

            // TODO Handle the lookup of the product....
            rc.response().end("static");

        });

        mainRouter.route("/.*\\.templ").handler(rc -> {

            // TODO Handle the lookup of the product....
            rc.response().end("templ");

        });

        //restAPI的URL变为：/productsAPI/products/product1234
        mainRouter.mountSubRouter("/productsAPI", restAPI);
        vertx.createHttpServer().requestHandler(mainRouter::accept).listen(8080);
    }
}

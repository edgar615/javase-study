package com.edgar.vertx.web.handlebars;

import com.edgar.vertx.web.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.templ.HandlebarsTemplateEngine;
import io.vertx.ext.web.templ.TemplateEngine;

/**
 * Created by Administrator on 2015/9/7.
 */
public class Server extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(Server.class);
    }

    @Override
    public void start() throws Exception {
        TemplateEngine engine = HandlebarsTemplateEngine.create();

        Router router = Router.router(vertx);
        router.get().handler(rc -> {
           rc.put("name", "vert.x web");

            engine.render(rc, "templates/index.hbs", res -> {
                if (res.succeeded()) {
                    rc.response().end(res.result());
                } else {
                    rc.fail(res.cause());
                }
            });
        });
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
}

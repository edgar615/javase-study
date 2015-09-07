package com.edgar.vertx.web.session;

import com.edgar.vertx.web.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;

/**
 * Created by Administrator on 2015/9/7.
 */
public class SessionServer extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(SessionServer.class);
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);

        router.route().handler(CookieHandler.create());
        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));

        router.route().handler(rc -> {
            Session session = rc.session();

            Integer cnt = session.get("hitcount");
            cnt = (cnt == null ? 0 : cnt) + 1;
            session.put("hitcount", cnt);
            rc.response().putHeader("content-type", "text/html")
                    .end("<html><body><h1>Hitcount: " + cnt + "</h1></body></html>");
        });
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
}

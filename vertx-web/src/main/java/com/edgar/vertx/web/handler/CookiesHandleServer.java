package com.edgar.vertx.web.handler;

import com.edgar.vertx.web.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;

import java.util.Set;

/**
 * Created by Administrator on 2015/9/6.
 */
public class CookiesHandleServer extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(CookiesHandleServer.class);
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        Route route = router.route().handler(CookieHandler.create());

        //You use getCookie to retrieve a cookie by name, or use cookies to retrieve the entire set.
        //To remove a cookie, use removeCookie.
        //To add a cookie use addCookie.
        router.route("some/path/").handler(routingContext -> {

            Cookie someCookie = routingContext.getCookie("mycookie");
            String cookieValue = someCookie.getValue();
            // Do something with cookie...
            // Add a cookie - this will get written back in the response automatically
            routingContext.addCookie(Cookie.cookie("othercookie", "somevalue"));
        });
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
}

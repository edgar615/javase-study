package com.edgar.vertx.config;

import com.edgar.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

/**
 * Created by Administrator on 2015/9/7.
 */
public class ConfigServer extends AbstractVerticle {

//    public static void main(String[] args) {
//        Runner.runExample(ConfigServer.class);
//    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        vertx.createHttpServer().requestHandler(req -> {
            req.response().putHeader("content-type", "text/html").end("<h1>Hello from my first " +
                    "Vert.x 3 application</h1>");
        }).listen(config().getInteger("http.port", 8080),result -> {
            if (result.succeeded()) {
                startFuture.complete();
            } else {
                startFuture.fail(result.cause());
            }
        });

        //java -jar target/my-first-app-1.0-SNAPSHOT-fat.jar -conf src/main/conf/my-application-conf.json
        //How does that work ? Remember, our fat jar is using the Starter class (provided by Vert.x) to launch our application. This class is reading the -conf parameter and create the corresponding deployment options when deploying our verticle.
    }
}

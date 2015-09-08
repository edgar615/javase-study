package com.edgar.vertx.config;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by Administrator on 2015/9/8.
 */
@RunWith(VertxUnitRunner.class)
public class ConfigServerTest {
    private Vertx vertx;
    private Integer port;

    @Before
    public void setUp(TestContext context) throws IOException {
        vertx = Vertx.vertx();
        // Let's configure the verticle to listen on the 'test' port (randomly picked).
// We create deployment options and set the _configuration_ json object:
        ServerSocket socket = new ServerSocket(0);
        port = socket.getLocalPort();
        socket.close();

        DeploymentOptions options = new DeploymentOptions()
                .setConfig(new JsonObject().put("http.port", port)
                );
        vertx.deployVerticle(ConfigServer.class.getName(), options, context.asyncAssertSuccess());
    }

    @After
    public void tearDown(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void testConfigServer(TestContext context) {
        // This test is asynchronous, so get an async handler to inform the test when we are done.
        final Async async = context.async();

        // We create a HTTP client and query our application. When we get the response we check it contains the 'Hello'
// message. Then, we call the `complete` method on the async handler to declare this async (and here the test) done.
// Notice that the assertions are made on the 'context' object and are not Junit assert. This ways it manage the
// async aspect of the test the right way.
        vertx.createHttpClient().getNow(port, "localhost", "/", response -> {

            context.assertEquals(response.statusCode(), 200);
            context.assertEquals(response.headers().get("content-type"), "text/html");

            response.handler(body -> {
                context.assertTrue(body.toString().contains("Hello from my first"));
                async.complete();
            });
        });
    }
}

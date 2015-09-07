package com.edgar.vertx.tcp;

import com.edgar.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.streams.Pump;

/**
 * Created by Administrator on 2015/9/1.
 */
public class Server extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(Server.class);
    }

    @Override
    public void start() throws Exception {
        vertx.createNetServer().connectHandler(socket -> {
            Pump.pump(socket, socket).start();
        }).listen(1234);

        System.out.println("Echo server is now listening");
    }
}

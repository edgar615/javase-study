package com.edgar.vertx.tcp;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;

/**
 * Created by Administrator on 2015/9/1.
 */
public class ClientExample {
    public static void main(String[] args) {
        //Creating a TCP client
        NetClient client = Vertx.vertx().createNetClient();

        //Configuring a TCP client
        NetClientOptions options = new NetClientOptions().setConnectTimeout(10000);
        client = Vertx.vertx().createNetClient(options);
        //Making connections
        client.connect(4321, "localhost", res -> {
            if (res.succeeded()) {
                System.out.println("Connected!");
                NetSocket socket = res.result();
            } else {
                System.out.println("failed to connect: " + res.cause().getMessage());
            }

            //Configuring connection attempts
            options.setReconnectAttempts(10).setReconnectInterval(500);
        });
    }
}

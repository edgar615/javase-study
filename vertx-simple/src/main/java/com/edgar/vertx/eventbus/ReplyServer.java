package com.edgar.vertx.eventbus;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;

/**
 * Created by Administrator on 2015/8/31.
 */
public class ReplyServer {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        EventBus eb = vertx.eventBus();
        eb.consumer("news.uk.sport", message -> {
            System.out.println("I have received a message: " + message.body());
            message.reply("how interesting!");
        });

        //If a reply is not received within that time, the reply handler will be called with a failure.
        //The default timeout is 30 seconds.
//        DeliveryOptions options = new DeliveryOptions();
//        options.setSendTimeout(30);
        eb.send("news.uk.sport", "Yay! Someone kicked a ball across a patch of grass", ar -> {
            if (ar.succeeded()) {
                System.out.println("Received reply: " + ar.result().body());
            }
        });

    }
}

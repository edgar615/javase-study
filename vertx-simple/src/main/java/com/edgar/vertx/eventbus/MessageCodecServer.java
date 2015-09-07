package com.edgar.vertx.eventbus;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;

/**
 * Created by Administrator on 2015/8/31.
 */
public class MessageCodecServer {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        EventBus eb = vertx.eventBus();


        eb.consumer("news.uk.sport", message -> {
            System.out.println("I have received a message: " + message.body());
        });

        MessageConsumer<String> consumer = eb.consumer("news.uk.sport");
        consumer.handler(message -> {
            System.out.println("I also have received a message: " + message.body());
        });

        //Publishing messages
        //That message will then be delivered to all handlers registered against the address news.uk.sport.
        eb.send("news.uk.sport", "Yay! Someone kicked a ball");
        eb.send("news.uk.sport", "2Yay! Someone kicked a ball");
        eb.send("news.uk.sport", "3Yay! Someone kicked a ball");

    }
}

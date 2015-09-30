package com.edgar.vertx.rxjava.eventbus;

import com.edgar.vertx.util.Runner;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.eventbus.EventBus;

import java.util.Random;

public class ZipReceiver extends AbstractVerticle {
    // Convenience method so you can run it in your IDE
    public static void main(String[] args) {
        Runner.runClusteredExample(ZipReceiver.class);
    }

    @Override
    public void start() throws Exception {
        Random random1 = new Random();
        EventBus eb = vertx.eventBus();
        eb.consumer("heatsensor1").
                toObservable().
                subscribe(message -> {
                    message.reply(9 + random1.nextInt(5));
                });
        eb.consumer("heatsensor2").
                toObservable().
                subscribe(message -> {
                    message.reply(10 + random1.nextInt(3));
                });
    }
}
package com.edgar.vertx.rxjava.eventbus;

import com.edgar.vertx.util.Runner;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.eventbus.EventBus;

/**
 * Created by Administrator on 2015/9/30.
 */
public class Receiver extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runClusteredExample(Receiver.class);
    }

    @Override
    public void start() throws Exception {
        EventBus eb = vertx.eventBus();
        eb.consumer("news-feed").toObservable()
                .subscribe(msg -> System.out.println("Received news: " + msg.body()));
        System.out.println("Ready!");
    }
}

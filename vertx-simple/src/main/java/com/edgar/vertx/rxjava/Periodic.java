package com.edgar.vertx.rxjava;

import com.edgar.vertx.util.Runner;
import io.vertx.rxjava.core.AbstractVerticle;
import rx.Observable;
import rx.Scheduler;

import java.util.concurrent.TimeUnit;

public class Periodic extends AbstractVerticle {
    // Convenience method so you can run it in your IDE
    public static void main(String[] args) {
        Runner.runExample(Periodic.class);
    }

    @Override
    public void start() throws Exception {
//
        Scheduler scheduler = io.vertx.rxjava.core.RxHelper.scheduler(vertx);
// Create a periodic event stream using Vertx scheduler
        Observable<Long> o = Observable.
                timer(0, 1000, TimeUnit.MILLISECONDS, scheduler);
        o.subscribe(item -> {
            System.out.println("Got item " + item);
        });
    }
}
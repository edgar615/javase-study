package com.edgar.vertx.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

/**
 * Created by Administrator on 2015/8/31.
 */
public class MyAsynVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        vertx.deployVerticle("com.foo.OtherVerticel", res -> {
            if (res.succeeded()) {
                startFuture.complete();
            } else {
                startFuture.failed();
            }
        });

        //Worker verticle instances are never executed concurrently by Vert.x by more than one thread, but can executed by different threads at different times.
//        DeploymentOptions options = new DeploymentOptions().setWorker(true);
//        vertx.deployVerticle("com.mycompany.MyOrderProcessorVerticle", options);
    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        //INFO: You don’t need to manually undeploy child verticles started by a verticle, in the verticle’s stop method.
        // Vert.x will automatically undeploy any child verticles when the parent is undeployed.
//        obj.doSomethingThatTakesTime(res -> {
//            if (res.succeeded()) {
//                startFuture.complete();
//            } else {
//                startFuture.fail();
//            }
//        });
    }
}

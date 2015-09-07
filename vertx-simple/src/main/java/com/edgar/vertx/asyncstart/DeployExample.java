package com.edgar.vertx.asyncstart;

import com.edgar.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;

/**
 * Created by Administrator on 2015/8/31.
 */
public class DeployExample extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runClusteredExample(DeployExample.class);
    }

    @Override
    public void start() throws Exception {
        System.out.println("Main verticle has started, let's deploy some others...");

        vertx.deployVerticle("com.edgar.vertx.asyncstart.OtherVerticle", res -> {
            if (res.succeeded()) {
                String deploymentID = res.result();
                System.out.println("Other verticle deployed ok, deploymentID = " + deploymentID);

                //undeploy
                vertx.undeploy(deploymentID, res2 -> {
                    if (res2.succeeded()) {
                        System.out.println("Undeployed ok!");
                    } else {
                        res2.cause().printStackTrace();
                    }
                });
            } else {
                res.cause().printStackTrace();
            }
        });

    }
}

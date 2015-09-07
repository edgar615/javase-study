package com.edgar.vertx.deploy;

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

        // Different ways of deploying verticles
        // Deploy a verticle and don't wait for it to start
        vertx.deployVerticle("com.edgar.vertx.deploy.OtherVerticle");

        // Deploy another instance and want for it to start
        vertx.deployVerticle("com.edgar.vertx.deploy.OtherVerticle", res -> {
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

        // Deploy specifying some config
        JsonObject config = new JsonObject().put("foo", "bar");
        vertx.deployVerticle("com.edgar.vertx.deploy.OtherVerticle", new DeploymentOptions().setConfig(config));

        //deploy 10 instances
        vertx.deployVerticle("com.edgar.vertx.deploy.OtherVerticle", new DeploymentOptions().setInstances(10));

        //deploy it as a worker verticle
        vertx.deployVerticle("com.edgar.vertx.deploy.OtherVerticle", new DeploymentOptions().setWorker(true));

    }
}

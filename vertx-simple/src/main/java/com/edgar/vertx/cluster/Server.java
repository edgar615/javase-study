package com.edgar.vertx.cluster;

import com.edgar.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

/**
 * Created by Administrator on 2015/9/7.
 */
public class Server {

    public static void main(String[] args) {
        ClusterManager mgr = new HazelcastClusterManager();
        VertxOptions options = new VertxOptions().setClusterManager(mgr);
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                vertx.createHttpServer().requestHandler(req -> {
//                    req.response().end("hello");
                });
            } else {
                // failed!
            }
        });
    }

}

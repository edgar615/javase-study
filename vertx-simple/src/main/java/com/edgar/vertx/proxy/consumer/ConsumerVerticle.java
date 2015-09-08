package com.edgar.vertx.proxy.consumer;

import com.edgar.vertx.proxy.producer.ProcessorService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

/**
 * A verticle consuming the provided {@link ProcessorService} service.
 */
public class ConsumerVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        ProcessorService service = ProcessorService.createProxy(vertx, "vertx.processor");
        JsonObject document = new JsonObject().put("name", "vertx");
        service.process(document, (r) -> {
            if (r.succeeded()) {
                System.out.println(r.result().encodePrettily());
            } else {
                System.out.println(r.cause());
            }
        });
    }
}
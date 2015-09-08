package com.edgar.vertx.proxy.producer;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

/**
 * Service implementation.
 * <p>
 * Just add `approved` to the given document.
 */
public class ProcessorServiceImpl implements ProcessorService {
    @Override
    public void process(JsonObject document, Handler<AsyncResult<JsonObject>> resultHandler) {
        System.out.println("Processing...");
        JsonObject result = document.copy();
        result.put("approved", true);
        resultHandler.handle(Future.succeededFuture(result));
    }
}
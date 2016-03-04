package com.edgar.vertx.rxjava;

import com.edgar.vertx.util.Runner;
import io.vertx.core.http.HttpMethod;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.http.HttpClient;
import io.vertx.rxjava.core.http.HttpClientRequest;

public class Client extends AbstractVerticle {
    // Convenience method so you can run it in your IDE
    public static void main(String[] args) {
        Runner.runExample(Client.class);
    }

    @Override
    public void start() throws Exception {
        HttpClient client = vertx.createHttpClient();
        HttpClientRequest req = client.request(HttpMethod.GET, 9999, "localhost", "/tasks");
        req.toObservable().
// Status code check and -> Observable<Buffer>
        flatMap(resp -> {
    if (resp.statusCode() != 200) {
        throw new RuntimeException("Wrong status code " + resp.statusCode());
    }
    return resp.toObservable();
}).
                subscribe(data -> System.out.println("Server content " + data.toString("UTF-8")));
// End request
        req.end();
    }
}
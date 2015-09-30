package com.edgar.vertx.rxjava;

import com.edgar.vertx.util.Runner;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.buffer.Buffer;
import io.vertx.rxjava.core.http.HttpClient;
import io.vertx.rxjava.core.http.HttpClientRequest;
import io.vertx.rxjava.core.http.HttpClientResponse;
import rx.Observable;

/**
 *

 Same as simple example however the client applies several operations on this observable to end with the http client response:

 flatMap transforms the Observable<HttpClientResponse> → Observable<Buffer>

 reduce merge all response buffers in a single buffer

 map transform the buffer to a string

 subscribe delivers the response content


 */
public class ZipClient extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(ZipClient.class);
    }

    @Override
    public void start() throws Exception {
        HttpClient client = vertx.createHttpClient();
        HttpClientRequest request = client.request(HttpMethod.GET, 8080, "localhost", "/products/prod7340");
        HttpClientRequest request2 = client.request(HttpMethod.GET, 8080, "localhost", "/products/prod3568");
        // Turn the requests responses into Observable<JsonObject>

        Observable<JsonObject> obs1 = request.toObservable().flatMap(HttpClientResponse::toObservable)
                .map(buf -> new JsonObject(buf.toString("UTF-8")));
        Observable<JsonObject> obs2 = request2.toObservable().flatMap(HttpClientResponse::toObservable).
                map(buf -> new JsonObject(buf.toString("UTF-8")));

        // Combine the responses with the zip into a single response
        obs1.zipWith(obs2, (b1, b2) -> new JsonObject().put("req1", b1).put("req2", b2))
                .subscribe(json -> {
                    System.out.println("Got combined result " + json);
                },
                        err -> {
                            err.printStackTrace();
                        });
        request.end();
        request2.end();

    }
}

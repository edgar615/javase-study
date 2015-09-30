package com.edgar.vertx.rxjava;

import com.edgar.vertx.util.Runner;
import io.vertx.core.http.HttpMethod;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.buffer.Buffer;
import io.vertx.rxjava.core.http.HttpClient;
import io.vertx.rxjava.core.http.HttpClientRequest;
import rx.Observable;

/**
 *

 Same as simple example however the client applies several operations on this observable to end with the http client response:

 flatMap transforms the Observable<HttpClientResponse> → Observable<Buffer>

 reduce merge all response buffers in a single buffer

 map transform the buffer to a string

 subscribe delivers the response content


 */
public class ReduceClient extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(ReduceClient.class);
    }

    @Override
    public void start() throws Exception {
        HttpClient client = vertx.createHttpClient();
        HttpClientRequest request = client.request(HttpMethod.GET, 8080, "localhost", "/");
        request.toObservable()
                // Status code check and -> Observable<Buffer>
                        .flatMap(resp -> {
                    if (resp.statusCode() != 200) {
                        throw new RuntimeException("Wrong status code " + resp.statusCode());
                    }
                    return Observable.just(Buffer.buffer()).mergeWith(resp.toObservable());
                })
                        // Reduce all buffers in a single buffer
                .reduce(Buffer::appendBuffer).
        // Turn in to a string
        map(buffer -> buffer.toString("UTF-8")).


// Get a single buffer
        subscribe(data -> System.out.println("Server content " + data));

        request.end();
    }
}

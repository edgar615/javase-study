package com.edgar.vertx.rxjava;

import com.edgar.vertx.util.Runner;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.RxHelper;
import io.vertx.rxjava.core.http.HttpClient;
import io.vertx.rxjava.core.http.HttpClientRequest;
import io.vertx.rxjava.core.http.HttpClientResponse;
import rx.Observable;

/**
 * Created by Administrator on 2015/9/30.
 */
public class UnmarshallClient extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(UnmarshallClient.class);
    }

    static class Product {
        private String name;

        private String id;

        private double price;

        private int weight;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }
    @Override
    public void start() throws Exception {
        HttpClient client = vertx.createHttpClient();
        HttpClientRequest request = client.request(HttpMethod.GET, 8080, "localhost", "/products/prod7340");
        request.toObservable().flatMap(HttpClientResponse::toObservable)
                .lift(RxHelper.unmarshaller(Product.class))
                .subscribe(data -> {
                    System.out.println("Got response " + data.getName());
                });

        request.end();
    }
}

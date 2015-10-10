package com.edgar.vertx.rxjava;

import com.edgar.vertx.util.Runner;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.ResultSet;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.ext.jdbc.JDBCClient;
import rx.Observable;

/**
 * Created by Administrator on 2015/9/30.
 */
public class JdbcClient2 extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(JdbcClient2.class);
    }

    @Override
    public void start() throws Exception {
        JsonObject config = new JsonObject()
                .put("driver_class", "com.mysql.jdbc.Driver")
                .put("url", "jdbc:mysql://localhost:3306/fire")
                .put("max_pool_size", 30)
                .put("user", "root");

        JDBCClient jdbc = JDBCClient.createShared(vertx, config);

        jdbc.getConnectionObservable().subscribe(conn -> {
                    Observable<ResultSet> resa = conn.updateObservable("DROP TABLE test").
                            flatMap(result -> conn.updateObservable("CREATE TABLE test(col VARCHAR(20))")).
                            flatMap(result -> conn.updateObservable("INSERT INTO test (col) VALUES ('val1')")).
                            flatMap(result -> conn.updateObservable("INSERT INTO test (col) VALUES ('val2')")).
                            flatMap(result -> conn.queryObservable("SELECT * FROM test"));

                    resa.subscribe(resultSet -> {
                        System.out.println("Results : " + resultSet.getRows());
                    }, err -> {
                        System.out.println("Database problem");
                        err.printStackTrace();
                    });
                },
                // Could not connect
                err -> {
                    err.printStackTrace();
                });
    }
}

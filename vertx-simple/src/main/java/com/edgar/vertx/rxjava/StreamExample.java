package com.edgar.vertx.rxjava;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.FileSystem;
import io.vertx.core.file.OpenOptions;
import io.vertx.rx.java.RxHelper;
import rx.Observable;

/**
 * Created by Administrator on 2015/9/30.
 */
public class StreamExample {

    public static void main(String[] args) {
        FileSystem fileSystem = Vertx.vertx().fileSystem();
        fileSystem.open("d:/data.txt", new OpenOptions(), result -> {
            AsyncFile file = result.result();
            Observable<Buffer> observable = RxHelper.toObservable(file);
            observable.forEach(data -> System.out.println("Read data: " + data.toString("UTF-8")));
        });
    }
}

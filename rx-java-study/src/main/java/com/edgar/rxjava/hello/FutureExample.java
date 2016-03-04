package com.edgar.rxjava.hello;

import rx.Observable;
import rx.functions.Action1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by Edgar on 2016/1/22.
 *
 * @author Edgar  Date 2016/1/22
 */
public class FutureExample {
  public static void main(String[] args) {
    ExecutorService exec = Executors.newFixedThreadPool(1);
    Future<String> future = exec.submit(new Callable<String>() {
      @Override
      public String call() throws Exception {
        TimeUnit.SECONDS.sleep(3);
        return "hello";
      }
    });

    Observable.from(future).subscribe(new Action1<String>() {
      @Override
      public void call(String o) {
        System.out.println(o);
        exec.shutdown();
      }
    });
  }
}

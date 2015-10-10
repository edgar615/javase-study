package com.edgar.rxjava.hello;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by Administrator on 2015/10/10.
 */
public class Second {
    public static void main(String[] args) {
        //1
        Observable<String> observable = Observable.just("Hello RxJava");
        Action1<String> subscriber = new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        };
        observable.subscribe(subscriber);

        //or
        Observable.just("Hello RxJava").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        });

        //or
        Observable.just("Hello RxJava").subscribe(s -> System.out.println(s));
    }
}

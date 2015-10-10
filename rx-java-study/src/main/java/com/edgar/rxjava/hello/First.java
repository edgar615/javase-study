package com.edgar.rxjava.hello;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2015/10/10.
 */
public class First {
    public static void main(String[] args) {
        // 创建observable
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello RxJava");
                subscriber.onCompleted();
            }
        });

// 创建subscriber
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        };

// 订阅
        observable.subscribe(subscriber);
    }
}

package com.edgar.rxjava.hello;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Administrator on 2015/10/10.
 */
public class Thread {

    /**
     * 在开发过程中，为了避免阻塞UI线程，我们可能需要将某些工作放到指定线程执行。
     * 在RxJava中，你可以通过subscribeOn()来指定Observer的运行线程，通过observeOn()指定Subscriber的运行线程。这两个方法都是operator，因此它们可以像所有operator那样作用于任何的Observable。
     */
    public static void main(String[] args) {

//        Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                log("Observable on Thread -> " + Thread.currentThread().getName());
//                subscriber.onNext("MultiThreading");
//                subscriber.onCompleted();
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(s -> {
//                    log("Subscriber on Thread -> " + Thread.currentThread().getName());
//                });
    }
}

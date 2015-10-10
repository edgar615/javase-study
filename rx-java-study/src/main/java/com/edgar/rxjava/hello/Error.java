package com.edgar.rxjava.hello;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2015/10/10.
 */
public class Error {
    public static void main(String[] args) {
        Observable.just("Hello, world!")
                .map(s -> potentialException(s))
//                .map(s -> anotherPotentialException(s))
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        System.out.println(s);
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("Completed!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Ouch!");
                    }
                });
    }

    private static String potentialException(String s) {
        if (s != null) {
            throw new RuntimeException("haha");
        }
        return s;
    }
}

package com.edgar.rxjava.hello;


import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Administrator on 2015/10/10.
 */
public class HelloWorld {

    public static void main(String[] args) {
        hello("Edgar", "Leona");
    }

    public static void hello(String ... names) {
        //These converted Observables will synchronously invoke the onNext( ) method of any subscriber that subscribes to them, for each item to be emitted by the Observable, and will then invoke the subscriber’s onCompleted( ) method.
        Observable.from(names).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("Hello " + s + "!");
            }
        });
    }
}

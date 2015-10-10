package com.edgar.rxjava.hello;

import rx.Observable;
import rx.functions.Func1;

import java.util.List;

/**
 * Created by Administrator on 2015/10/10.
 */
public class From {
    public static void main(String[] args) {
        //Observable.from(), that takes a collection of items and emits each them one at a time:
        Observable.from(new String[]{"url1", "url2", "url3"}).subscribe(url -> System.out.println(url));
    }
}

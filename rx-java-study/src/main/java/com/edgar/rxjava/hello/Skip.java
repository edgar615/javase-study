package com.edgar.rxjava.hello;

import rx.Observable;

/**
 * Created by Administrator on 2015/10/10.
 */
public class Skip {
    public static void main(String[] args) {
        Observable.from(new Integer[]{1,2,3,4,5,6,7,8,9,10}).skip(3).take(5).subscribe(url -> System.out.println(url));

    }
}

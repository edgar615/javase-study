package com.edgar.designpattern.singleton.simple;

/**
 * Created by Administrator on 2015/5/19.
 */
public class Singleton3 {
    private volatile static Singleton3 instance;

    private Singleton3() {
    }

    public static Singleton3 getInstance() {
        if(instance == null) {
            synchronized (Singleton3.class) {
                if (instance == null) {
                    instance = new Singleton3();
                }
            }
        }
        return instance;
    }
}

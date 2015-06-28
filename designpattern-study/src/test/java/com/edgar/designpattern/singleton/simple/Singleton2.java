package com.edgar.designpattern.singleton.simple;

/**
 * Created by Administrator on 2015/5/19.
 */
public class Singleton2 {
    private static class SingletonHolder {
        private static final Singleton2 INSTANCE = new designpattern.singleton.Singleton2();
    }
    private Singleton2() {

    }
    public static Singleton2 getInstance() {
        return SingletonHolder.INSTANCE;
    }
}

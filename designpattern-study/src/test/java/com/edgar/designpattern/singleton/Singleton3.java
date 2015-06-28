package com.edgar.designpattern.singleton;

/**
 * 双重检查加锁
 * 
 * @author Administrator
 * 
 */
public class Singleton3 {

	private volatile static Singleton3 uniqueSingleton;

	public static Singleton3 getInstance() {
		if (uniqueSingleton == null) {
			synchronized (Singleton3.class) {
				if (uniqueSingleton == null) {
					uniqueSingleton = new Singleton3();
				}
			}
		}
		return uniqueSingleton;
	}

	private Singleton3() {
	}

}

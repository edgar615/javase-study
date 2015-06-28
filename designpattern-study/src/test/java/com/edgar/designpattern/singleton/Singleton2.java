package com.edgar.designpattern.singleton;

/**
 * 如果应用程序总是创建和使用单例实例，或者创建和运行的负担不重，可以使用“急切”创建此对象
 * @author Administrator
 *
 */
public class Singleton2 {
	
	private static Singleton2 uniqueSingleton = new Singleton2();
	
	public static Singleton2 getInstance() {
		return uniqueSingleton;
	}

	private Singleton2() {
	}

}

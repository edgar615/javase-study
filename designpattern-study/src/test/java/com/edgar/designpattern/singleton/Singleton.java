package com.edgar.designpattern.singleton;

public class Singleton {
	
	private static Singleton uniqueSingleton;
	
	public static synchronized Singleton getInstance() {
		if (uniqueSingleton == null) {
			uniqueSingleton = new Singleton();
		}
		return uniqueSingleton;
	}

	private Singleton() {
	}

}

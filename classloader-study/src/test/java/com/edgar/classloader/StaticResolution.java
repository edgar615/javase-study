package com.edgar.classloader;

public class StaticResolution {
	
	public static void sayHello() {
		System.out.println("hello world");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StaticResolution.sayHello();
	}

}

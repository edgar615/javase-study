package com.edgar.classloader;

public class SubClass extends SuperClass {
	static {
		System.out.println("SubClass init!");
	}
}

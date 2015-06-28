package com.edgar.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyServiceHandler implements InvocationHandler {

	private Object target;

	public MyServiceHandler(Object target) {
		this.target = target;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println(1);
		return method.invoke(target, args);
	}

}

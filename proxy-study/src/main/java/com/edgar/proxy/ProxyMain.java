package com.edgar.proxy;

import java.lang.reflect.Proxy;

public class ProxyMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IMyService myService = new MyServiceImpl();
		myService.say("Hello");

		IMyService myServiceProxy = (IMyService) Proxy.newProxyInstance(
				myService.getClass().getClassLoader(), myService.getClass().getInterfaces(),
				new MyServiceHandler(myService));
		myServiceProxy.say("proxy");
	}

}

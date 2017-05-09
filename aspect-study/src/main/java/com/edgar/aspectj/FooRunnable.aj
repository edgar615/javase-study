package com.edgar.aspectj;

public aspect FooRunnable {
	declare parents : Foo implements Runnable;

	public void Foo.run() {
		System.out.println("in Foo.run()");
	}
}

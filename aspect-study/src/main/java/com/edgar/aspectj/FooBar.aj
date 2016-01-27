package com.edgar.aspectj;

public aspect FooBar {
	void Foo.bar() {
		System.out.println("in Foo.bar()");
	}
}

package com.edgar.aspectj;

public aspect FooNew {
	public Foo.new(String param) {
		super();
		 System.out.println("in Foo(string param)");
	}
}

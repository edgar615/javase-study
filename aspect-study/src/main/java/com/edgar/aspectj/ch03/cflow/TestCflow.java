package com.edgar.aspectj.ch03.cflow;

public class TestCflow {

	public void foo() {
		System.out.println("foo......");
	}

	public void bar() {
		foo();
		System.out.println("bar.........");
	}

	public void testMethod() {
		bar();
		foo();
	}
	
	public static void main(String[] args) throws Exception {
		TestCflow testCflow = new TestCflow();
		testCflow.testMethod();
	}

}
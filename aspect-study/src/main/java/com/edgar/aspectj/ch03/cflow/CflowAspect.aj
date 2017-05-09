package com.edgar.aspectj.ch03.cflow;

public aspect CflowAspect {

	pointcut barPointcut() : execution(* TestCflow.bar(..));
	
	pointcut fooPointcut() : execution(* TestCflow.foo(..));
	
	pointcut barcflow() : cflow(barPointcut());
	
	pointcut fooInBar() : barcflow() && fooPointcut() && !within(CflowAspect);
	
	
	before() : fooInBar(){
		System.out.println("Enter:" + thisJoinPoint);
	}
	
}

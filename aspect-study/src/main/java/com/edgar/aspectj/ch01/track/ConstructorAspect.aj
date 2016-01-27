package com.edgar.aspectj.ch01.track;

import com.edgar.aspectj.ch01.MessageCommunicator;

public aspect ConstructorAspect {
	pointcut constructorPointcut()  : call(MessageCommunicator.new());
	
	before() : constructorPointcut() {
		System.out.println(thisJoinPoint.getSourceLocation());
	}
}

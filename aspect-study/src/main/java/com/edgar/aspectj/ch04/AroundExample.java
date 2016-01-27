package com.edgar.aspectj.ch04;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AroundExample {
	@Pointcut("call(void com.edgar.aspectj.ch04.Account+.debit(..))")
	public void aroundPointCut() {
	}

	@Around("aroundPointCut()")
	public Object around(ProceedingJoinPoint pj) throws Throwable {
		System.out.println("around before proceed");
		Object retVal = pj.proceed();
		System.out.println("around after proceed");
		return retVal;
	}
}
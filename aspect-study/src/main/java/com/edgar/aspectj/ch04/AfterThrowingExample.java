package com.edgar.aspectj.ch04;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AfterThrowingExample {
	@Pointcut("execution(void com.edgar.aspectj.ch04.Account+.debit(..))")
	public void afterThrowingPointcut() {
	}

	@AfterThrowing("afterThrowingPointcut()")
	public void afterThrowing() {
		System.out.println("afterThrowing() ");
	}

	@AfterThrowing(pointcut = "afterThrowingPointcut()", throwing = "ex")
	public void afterThrowing(InsufficientBalanceException ex) {
		System.out.println(ex);
	}
}
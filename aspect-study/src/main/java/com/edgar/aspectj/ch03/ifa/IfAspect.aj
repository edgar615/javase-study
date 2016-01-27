package com.edgar.aspectj.ch03.ifa;

import org.aspectj.weaver.patterns.IfPointcut;

import com.edgar.aspectj.ch03.Account;

public aspect IfAspect {

	pointcut IfPointcut() :
			if (
				thisJoinPoint.getThis() instanceof Account
				&& ((Account) thisJoinPoint.getThis()).getBalance() > 300d)
				&& withincode(* Account.debit(..));

	before() : IfPointcut() && !within(IfAspect) {
		System.out.println(thisJoinPoint.getStaticPart().getSourceLocation());
		System.out.println("if");
	}
}

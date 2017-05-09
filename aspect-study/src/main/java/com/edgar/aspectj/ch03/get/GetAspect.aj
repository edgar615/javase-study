package com.edgar.aspectj.ch03.get;

import com.edgar.aspectj.ch03.Account;

public aspect GetAspect {
	pointcut GetBalancePointcut()  : get(float Account.balance);

	before() : GetBalancePointcut() {
	}

	float around() : GetBalancePointcut() {
		return proceed();
	}

	after() : GetBalancePointcut() {
	}

	after() returning(float balance) : GetBalancePointcut() {
		System.out.println(balance);
	}
}

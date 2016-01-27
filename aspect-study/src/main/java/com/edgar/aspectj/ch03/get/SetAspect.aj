package com.edgar.aspectj.ch03.get;

import com.edgar.aspectj.ch03.Account;

public aspect SetAspect {

	pointcut setBalancePointcut() : set(* Account.balance);

	pointcut setBalanceFieldPointcut(float balance) : set(* Account.balance) && args(balance);

	before(float balance) : setBalanceFieldPointcut(balance) {
		System.out.println("setBalance：" + balance);
	}
	
	before() : setBalancePointcut() {
	}

}

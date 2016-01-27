package com.edgar.aspectj.ch03.args;

import com.edgar.aspectj.ch03.Account;

public aspect ArgsAspect {

	pointcut callPointcut(float amount) : call(public void Account.debit(float)) && args(amount);

	before(float amount) : callPointcut(amount) {
		System.out.println("parameter amount : " + amount);
	}

}

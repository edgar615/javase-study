package com.edgar.aspectj.ch03.get;

import com.edgar.aspectj.ch03.SavingsAccount;

public aspect ConstructorAspect {

	pointcut constructorCallPointcut()  : call(SavingsAccount.new(int));

	pointcut constructorExePointcut()   : execution(SavingsAccount.new(int));

	pointcut constructorInitPointcut()   : initialization(SavingsAccount.new(int));

	pointcut constructorPreInitPointcut()   : preinitialization(SavingsAccount.new(int));

	pointcut constructorStaticInitPointcut()   : staticinitialization(SavingsAccount);

	before() : constructorCallPointcut() {
		System.out.println("constructorCallPointcut " + thisJoinPoint.getSourceLocation());
	}

	before() : constructorExePointcut() {
		System.out.println("constructorExePointcut " + thisJoinPoint.getSourceLocation());
	}

	before() : constructorInitPointcut() {
		System.out.println("constructorInitPointcut " + thisJoinPoint.getSourceLocation());
	}

	before() : constructorPreInitPointcut() {
		System.out.println("constructorPreInitPointcut " + thisJoinPoint.getSourceLocation());
	}

	before() : constructorStaticInitPointcut() {
		System.out.println("constructorStaticInitPointcut " + thisJoinPoint.getSourceLocation());
	}
}

package com.edgar.designpattern.more;

public class ConcreteStrategyB implements Strategy {
	@Override
	public void execute(int x) {
		System.out.println("executing strategy B: x = " + x);
	}
}
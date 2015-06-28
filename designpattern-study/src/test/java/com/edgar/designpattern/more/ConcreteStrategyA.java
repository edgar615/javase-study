package com.edgar.designpattern.more;

public class ConcreteStrategyA implements Strategy {
	@Override
	public void execute(int x) {
		System.out.println("executing strategy A: x = " + x);
	}
}
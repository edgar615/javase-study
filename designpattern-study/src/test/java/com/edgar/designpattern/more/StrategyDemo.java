package com.edgar.designpattern.more;

public class StrategyDemo {
	public static void main(String[] args) {
		Context context = new Context(new ConcreteStrategyA());
		context.executeStrategy(1);
		context.setStrategy(new ConcreteStrategyB());
		context.executeStrategy(2);
	}
}
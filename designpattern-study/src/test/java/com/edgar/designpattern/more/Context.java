package com.edgar.designpattern.more;

class Context {
	private Strategy strategy;

	public Context(Strategy strategy) {
		setStrategy(strategy);
	}

	public void executeStrategy(int x) {
		strategy.execute(x);
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}
}
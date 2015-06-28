package com.edgar.designpattern.strategy.ftoc;

public interface Application {

	void init();

	void idle();

	void cleanup();

	boolean done();
}

package com.edgar.designpattern.state.gumballmachinestate;

public interface State {
	public void createQuarter();

	public void ejectQuarter();

	public void turnCrank();

	public void dispense();
}

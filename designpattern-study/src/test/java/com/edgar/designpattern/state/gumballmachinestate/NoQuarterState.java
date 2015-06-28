package com.edgar.designpattern.state.gumballmachinestate;

public class NoQuarterState implements State {

	GumballMachine gumballMachine;

	public NoQuarterState(GumballMachine gumballMachine) {
		super();
		this.gumballMachine = gumballMachine;
	}

	@Override
	public void createQuarter() {
		System.out.println("You createed a quarter");
		gumballMachine.setState(gumballMachine.getHasQuarterState());

	}

	@Override
	public void ejectQuarter() {
		System.out.println("You haven't createed a quarter");
	}

	@Override
	public void turnCrank() {
		System.out.println("You turned, but there's no quarter");
	}

	@Override
	public void dispense() {
		System.out.println("You need to pay first");
	}

	public String toString() {
		return "waiting for quarter";
	}
}

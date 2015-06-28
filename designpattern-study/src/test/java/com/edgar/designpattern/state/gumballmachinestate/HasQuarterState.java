package com.edgar.designpattern.state.gumballmachinestate;

import java.util.Random;

public class HasQuarterState implements State {
	Random randomWinner = new Random(System.currentTimeMillis());
	GumballMachine gumballMachine;

	public HasQuarterState(GumballMachine gumballMachine) {
		super();
		this.gumballMachine = gumballMachine;
	}

	@Override
	public void createQuarter() {
		System.out.println("You can't create another quarter");
	}

	@Override
	public void ejectQuarter() {
		System.out.println("Quarter returned");
		gumballMachine.setState(gumballMachine.getNoQuarterState());
	}

	@Override
	public void turnCrank() {
		System.out.println("You turned...");
		int winner = randomWinner.nextInt(10);
		if ((winner == 0) && (gumballMachine.getCount() > 1)) {
			gumballMachine.setState(gumballMachine.getWinnerState());
		} else {
			gumballMachine.setState(gumballMachine.getSoldState());
		}
	}

	@Override
	public void dispense() {
		System.out.println("No gumball dispensed");
	}

	public String toString() {
		return "waiting for turn of crank";
	}
}

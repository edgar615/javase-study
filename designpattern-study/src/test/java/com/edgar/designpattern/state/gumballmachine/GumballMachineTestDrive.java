package com.edgar.designpattern.state.gumballmachine;

public class GumballMachineTestDrive {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GumballMachine gumballMachine = new GumballMachine(5);

		System.out.println(gumballMachine);

		gumballMachine.createQuarter();
		gumballMachine.turnCrank();

		System.out.println(gumballMachine);

		gumballMachine.createQuarter();
		gumballMachine.ejectQuarter();
		gumballMachine.turnCrank();

		System.out.println(gumballMachine);

		gumballMachine.createQuarter();
		gumballMachine.turnCrank();
		gumballMachine.createQuarter();
		gumballMachine.turnCrank();
		gumballMachine.ejectQuarter();

		System.out.println(gumballMachine);

		gumballMachine.createQuarter();
		gumballMachine.createQuarter();
		gumballMachine.turnCrank();
		gumballMachine.createQuarter();
		gumballMachine.turnCrank();
		gumballMachine.createQuarter();
		gumballMachine.turnCrank();

		System.out.println(gumballMachine);
	}

}

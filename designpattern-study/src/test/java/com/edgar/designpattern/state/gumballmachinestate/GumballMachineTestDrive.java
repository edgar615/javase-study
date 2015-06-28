package com.edgar.designpattern.state.gumballmachinestate;

public class GumballMachineTestDrive {

	public static void main(String[] args) {
		GumballMachine gumballMachine = new GumballMachine(5);

		System.out.println(gumballMachine);

		gumballMachine.createQuarter();
		gumballMachine.turnCrank();

		System.out.println(gumballMachine);

		gumballMachine.createQuarter();
		gumballMachine.turnCrank();
		gumballMachine.createQuarter();
		gumballMachine.turnCrank();

		System.out.println(gumballMachine);
	}
}
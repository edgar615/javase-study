package com.edgar.designpattern.factory.abstr;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PizzaStore pizzaStore = new NyPizzaStore();
		pizzaStore.orderPizza("cheese");

	}

}

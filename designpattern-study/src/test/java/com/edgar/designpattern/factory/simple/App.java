package com.edgar.designpattern.factory.simple;


public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SimplePizzaFactory pizzaFactory = new SimplePizzaFactory();
		PizzaStore pizzaStore = new PizzaStore(pizzaFactory);
		pizzaStore.orderPizza("cheese");
		
	}

}

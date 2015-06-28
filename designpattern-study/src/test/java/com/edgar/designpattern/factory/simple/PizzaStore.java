package com.edgar.designpattern.factory.simple;



public class PizzaStore {

	private SimplePizzaFactory pizzaFactory;

	public PizzaStore(SimplePizzaFactory pizzaFactory) {
		this.pizzaFactory = pizzaFactory;
	}

	public Pizza orderPizza(String type) {
		Pizza pizza = null;
		pizza = pizzaFactory.createPizza(type);
		pizza.prepare();
		pizza.bake();
		pizza.cut();
		pizza.box();
		return pizza;
	}
}

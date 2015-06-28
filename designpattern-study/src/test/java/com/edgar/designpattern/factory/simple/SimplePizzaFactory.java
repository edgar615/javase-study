package com.edgar.designpattern.factory.simple;



public class SimplePizzaFactory {

	public Pizza createPizza(String type) {
		if ("cheese".equals(type)) {
			return new CheesePizza();
		} else if ("clam".equals(type)) {
			return new ClamPizza();
		} else if ("pepperoni".equals(type)) {
			return new PepperoniPizza();
		} else if ("veggie".equals(type)) {
			return new VeggiePizza();
		}
		return null;
	}
}

package com.edgar.designpattern.factory;



public class NYPizzaStore extends PizzaStore {

	@Override
	public Pizza createPizza(String type) {
		if ("cheese".equals(type)) {
			return new NYStyleCheesePizza();
		} else if ("clam".equals(type)) {
			return new NYStyleClamPizza();
		} else if ("pepperoni".equals(type)) {
			return new NYStylePepperoniPizza();
		} else if ("veggie".equals(type)) {
			return new NYStyleVeggiePizza();
		}
		return null;
	}

}

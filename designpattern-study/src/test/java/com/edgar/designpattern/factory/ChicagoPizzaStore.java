package com.edgar.designpattern.factory;



public class ChicagoPizzaStore extends PizzaStore {

	@Override
	public Pizza createPizza(String type) {
		if ("cheese".equals(type)) {
			return new ChicagoStyleCheesePizza();
		} else if ("clam".equals(type)) {
			return new ChicagoStyleClamPizza();
		} else if ("pepperoni".equals(type)) {
			return new ChicagoStylePepperoniPizza();
		} else if ("veggie".equals(type)) {
			return new ChicagoStyleVeggiePizza();
		}
		return null;
	}

}

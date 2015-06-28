package com.edgar.designpattern.factory.abstr;



public class NyPizzaStore extends PizzaStore {

	@Override
	public Pizza createPizza(String type) {
		Pizza pizza = null;
		PizzaIngredientFactory ingredientFactory = new NYPizzaIngredientFactory();
		if ("cheese".equals(type)) {
			pizza = new CheesePizza(ingredientFactory);
			pizza.setName("New York Style Cheese Pizza");
		} else if ("clam".equals(type)) {
			pizza = new ClamPizza(ingredientFactory);
			pizza.setName("New York Style Clam Pizza");
		}
		return pizza;
	}

}

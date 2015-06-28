package com.edgar.designpattern.factory.abstr;


public class CheesePizza extends Pizza {
	
	PizzaIngredientFactory ingredientFactory;
	
	public CheesePizza(PizzaIngredientFactory ingredientFactory) {
		this.ingredientFactory = ingredientFactory;
	}

	@Override
	public void prepare() {
		System.out.println("preparing " + name);
		dough = ingredientFactory.createDaugh();
		sauce = ingredientFactory.createSauce();
		cheese = ingredientFactory.InsertCheese();
	}

}

package com.edgar.designpattern.factory.abstr;


public class ClamPizza extends Pizza {

	PizzaIngredientFactory ingredientFactory;

	public ClamPizza(PizzaIngredientFactory ingredientFactory) {
		this.ingredientFactory = ingredientFactory;
	}

	@Override
	public void prepare() {
		System.out.println("preparing " + name);
		dough = ingredientFactory.createDaugh();
		sauce = ingredientFactory.createSauce();
		cheese = ingredientFactory.InsertCheese();
		clam = ingredientFactory.InsertClam();
	}

}

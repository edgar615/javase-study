package com.edgar.designpattern.factory.abstr;


public interface PizzaIngredientFactory {

	Daugh createDaugh();

	Clam InsertClam();

	Sauce createSauce();

	Cheese InsertCheese();
	
	Pepperoni createPepperoni();
	
	Veggies[] createVeggies();
}
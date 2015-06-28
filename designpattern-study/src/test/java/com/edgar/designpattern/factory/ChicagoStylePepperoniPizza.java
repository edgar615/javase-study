package com.edgar.designpattern.factory;

public class ChicagoStylePepperoniPizza extends Pizza {
	public ChicagoStylePepperoniPizza() {
		this.name = "ChicagoStylePepperoniPizza";
		this.dough = "ChicagoStylePepperoniPizza Dough";
		this.sauce = "ChicagoStylePepperoniPizza sauce";
		
		this.toppings.add("ChicagoStylePepperoniPizza topping");
	}
}

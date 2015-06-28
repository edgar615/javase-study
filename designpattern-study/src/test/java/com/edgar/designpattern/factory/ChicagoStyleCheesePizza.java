package com.edgar.designpattern.factory;

public class ChicagoStyleCheesePizza extends Pizza {
	public ChicagoStyleCheesePizza() {
		this.name = "ChicagoStyleCheesePizza";
		this.dough = "ChicagoStyleCheesePizza Dough";
		this.sauce = "ChicagoStyleCheesePizza sauce";
		
		this.toppings.add("ChicagoStyleCheesePizza topping");
	}
}

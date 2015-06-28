package com.edgar.designpattern.factory;

public class ChicagoStyleVeggiePizza extends Pizza {
	public ChicagoStyleVeggiePizza() {
		this.name = "ChicagoStyleVeggiePizza";
		this.dough = "ChicagoStyleVeggiePizza Dough";
		this.sauce = "ChicagoStyleVeggiePizza sauce";
		
		this.toppings.add("ChicagoStyleVeggiePizza topping");
	}
}

package com.edgar.designpattern.factory;

public class ChicagoStyleClamPizza extends Pizza {
	public ChicagoStyleClamPizza() {
		this.name = "ChicagoStyleClamPizza";
		this.dough = "ChicagoStyleClamPizza Dough";
		this.sauce = "ChicagoStyleClamPizza sauce";
		
		this.toppings.add("ChicagoStyleClamPizza topping");
	}
}

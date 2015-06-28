package com.edgar.designpattern.factory;

public class NYStyleVeggiePizza extends Pizza {
	public NYStyleVeggiePizza() {
		this.name = "NYStyleVeggiePizza";
		this.dough = "NYStyleVeggiePizza Dough";
		this.sauce = "NYStyleVeggiePizza sauce";
		
		this.toppings.add("NYStyleVeggiePizza topping");
	}
}

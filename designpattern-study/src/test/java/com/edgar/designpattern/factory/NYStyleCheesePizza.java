package com.edgar.designpattern.factory;

public class NYStyleCheesePizza extends Pizza {
	public NYStyleCheesePizza() {
		this.name = "NYStyleCheesePizza";
		this.dough = "NYStyleCheesePizza Dough";
		this.sauce = "NYStyleCheesePizza sauce";
		
		this.toppings.add("NYStyleCheesePizza topping");
	}
}

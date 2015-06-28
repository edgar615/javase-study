package com.edgar.designpattern.factory;

public class NYStylePepperoniPizza extends Pizza {
	public NYStylePepperoniPizza() {
		this.name = "NYStylePepperoniPizza";
		this.dough = "NYStylePepperoniPizza Dough";
		this.sauce = "NYStylePepperoniPizza sauce";
		
		this.toppings.add("NYStylePepperoniPizza topping");
	}
}

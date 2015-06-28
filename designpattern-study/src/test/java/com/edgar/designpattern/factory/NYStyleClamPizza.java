package com.edgar.designpattern.factory;

public class NYStyleClamPizza extends Pizza {
	public NYStyleClamPizza() {
		this.name = "NYStyleClamPizza";
		this.dough = "NYStyleClamPizza Dough";
		this.sauce = "NYStyleClamPizza sauce";
		
		this.toppings.add("NYStyleClamPizza topping");
	}
}

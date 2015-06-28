package com.edgar.designpattern.factory;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PizzaStore nypizzaStore = new NYPizzaStore();
		nypizzaStore.orderPizza("cheese");
		
		PizzaStore chicagoStore = new ChicagoPizzaStore();
		chicagoStore.orderPizza("cheese");
	}

}

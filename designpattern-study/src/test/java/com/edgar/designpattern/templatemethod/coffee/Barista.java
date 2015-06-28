package com.edgar.designpattern.templatemethod.coffee;

public class Barista {

	public static void main(String[] args) {
		Tea tea = new Tea();
		CaffeineBeverage coffee = new Coffee();
		System.out.println("Making tea...");
		tea.prepareRecipe();
		System.out.println("Making coffee...");
		coffee.prepareRecipe();
	}
}
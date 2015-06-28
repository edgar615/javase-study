package com.edgar.designpattern.templatemethod.coffee;

public abstract class CaffeineBeverage {

	public CaffeineBeverage() {
		super();
	}

	public abstract void addCondiments();

	public abstract void brew();

	protected final void prepareRecipe() {
		boilWater();
		brew();
		pourInCup();
		addCondiments();
	}

	public void boilWater() {
		System.out.println("Boiling water");
	}

	public void pourInCup() {
		System.out.println("Pouring into cup");
	}

}
package com.edgar.designpattern.factory.abstr;


public class NYPizzaIngredientFactory implements PizzaIngredientFactory {

	@Override
	public Daugh createDaugh() {
		return new ThinCrustDaugh();
	}

	@Override
	public Clam InsertClam() {
		return new FreshClam();
	}

	@Override
	public Sauce createSauce() {
		return new MarinaraSauce();
	}

	@Override
	public Cheese InsertCheese() {
		return new ReggianoCheese();
	}

	@Override
	public Pepperoni createPepperoni() {
		return new SlicePepperoni();
	}

	@Override
	public Veggies[] createVeggies() {
		return new Veggies[] { new Garlic(), new Onion(), new Mushroom(),
				new RedPepper() };
	}

}
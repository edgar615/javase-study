package com.edgar.designpattern.factory.abstr;



public abstract class Pizza {
	
	String name;
	Daugh dough;
	Sauce sauce;
	Veggies[] veggies;
	Cheese cheese;
	Clam clam;
	
	public abstract void prepare();

	public void bake() {
		System.out.println("bake Pizza");
	}

	public void cut() {
		System.out.println("cut Pizza");
	}

	public void box() {
		System.out.println("box Pizza");
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}

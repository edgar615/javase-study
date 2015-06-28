package com.edgar.designpattern.factory.simple;

public class VeggiePizza extends Pizza {

	@Override
	public void prepare() {
		System.out.println("prepare VeggiePizza");
	}

	@Override
	public void bake() {
		System.out.println("bake VeggiePizza");
	}

	@Override
	public void cut() {
		System.out.println("cut VeggiePizza");
	}

	@Override
	public void box() {
		System.out.println("box VeggiePizza");
	}

}

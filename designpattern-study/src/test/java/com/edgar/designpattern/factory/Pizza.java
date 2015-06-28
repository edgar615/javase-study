package com.edgar.designpattern.factory;

import java.util.ArrayList;
import java.util.List;

public abstract class Pizza {
	
	String name;
	String dough;
	String sauce;
	List toppings = new ArrayList();
	
	public void prepare() {
		System.out.println("prepareing " + name);
		System.out.println("tossing dough...");
		System.out.println("adding sauce...");
		System.out.println("adding toppings ");
		for (int i = 0; i < toppings.size(); i++) {
			System.out.print("     " + toppings.get(i));
		}
	}

	public void bake() {
		System.out.println("bake Pizza");
	}

	public void cut() {
		System.out.println("cut Pizza");
	}

	public void box() {
		System.out.println("box Pizza");
	}

	public String getName() {
		return name;
	}
}

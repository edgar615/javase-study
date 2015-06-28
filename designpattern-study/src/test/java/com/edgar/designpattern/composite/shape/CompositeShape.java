package com.edgar.designpattern.composite.shape;

import java.util.ArrayList;
import java.util.List;

public class CompositeShape implements Shape {

	private List<Shape>  shapes = new ArrayList<Shape>();
	
	public void add(Shape s) {
		shapes.add(s);
	}

	@Override
	public void draw() {
		for (Shape shape : shapes) {
			shape.draw();
		}
	}
	
}

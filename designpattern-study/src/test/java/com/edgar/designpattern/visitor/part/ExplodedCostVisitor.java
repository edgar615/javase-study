package com.edgar.designpattern.visitor.part;

public class ExplodedCostVisitor implements PartVisitor {

	private double cost = 0;

	public double getCost() {
		return cost;
	}

	@Override
	public void visit(Assembly assembly) {

	}

	@Override
	public void visit(PiecePart pricePart) {
		cost += pricePart.getCost();
	}

}

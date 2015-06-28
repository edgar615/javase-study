package com.edgar.designpattern.visitor.part;

public class PiecePart implements Part {

	private String partNumber;
	private String description;
	private double cost;

	public PiecePart(String partNumber, String description, double cost) {
		this.partNumber = partNumber;
		this.description = description;
		this.cost = cost;
	}

	@Override
	public String getPartNumber() {
		return partNumber;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void accept(PartVisitor v) {
		v.visit(this);
	}

	public double getCost() {
		return cost;
	}

}

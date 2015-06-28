package com.edgar.designpattern.visitor.part;

import java.util.LinkedList;
import java.util.List;

public class Assembly implements Part {

	private final List<Part> parts = new LinkedList<Part>();
	private String partNumber;
	private String description;

	public Assembly(String partNumber, String description) {
		super();
		this.partNumber = partNumber;
		this.description = description;
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
		for (Part part : parts) {
			part.accept(v);
		}
	}

	public void add(Part part) {
		parts.add(part);
	}

	public List<Part> getParts() {
		return parts;
	}

}

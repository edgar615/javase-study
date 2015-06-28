package com.edgar.designpattern.visitor.part;

public interface Part {

	String getPartNumber();

	String getDescription();

	void accept(PartVisitor v);
}

package com.edgar.designpattern.visitor.part;

public interface PartVisitor {

	void visit(Assembly assembly);

	void visit(PiecePart pricePart);
}

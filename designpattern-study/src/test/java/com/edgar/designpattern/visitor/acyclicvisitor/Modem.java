package com.edgar.designpattern.visitor.acyclicvisitor;

public interface Modem {

	void dial(String pno);

	void hangup();

	void send(char c);

	void recv();

	void accept(ModemVisitor v);
}

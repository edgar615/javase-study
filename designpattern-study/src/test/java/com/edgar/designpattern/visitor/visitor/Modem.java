package com.edgar.designpattern.visitor.visitor;

public interface Modem {

	void dial(String pno);

	void hangup();

	void send(char c);

	void recv();

	void accept(ModemVisitor v);
}

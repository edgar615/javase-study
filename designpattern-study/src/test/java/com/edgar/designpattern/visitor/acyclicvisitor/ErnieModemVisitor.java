package com.edgar.designpattern.visitor.acyclicvisitor;

public class ErnieModemVisitor implements ModemVisitor {

	public void visit(ErnieModem modem) {
		modem.configurationPattern = "C is too slow";
	}

}

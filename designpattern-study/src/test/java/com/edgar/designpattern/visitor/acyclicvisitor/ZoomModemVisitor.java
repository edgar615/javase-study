package com.edgar.designpattern.visitor.acyclicvisitor;

public class ZoomModemVisitor implements ModemVisitor {

	public void visit(ZoomModem modem) {
		modem.configurationValue = 42;

	}

}

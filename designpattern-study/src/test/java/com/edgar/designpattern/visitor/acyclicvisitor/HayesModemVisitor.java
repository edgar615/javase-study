package com.edgar.designpattern.visitor.acyclicvisitor;

public class HayesModemVisitor implements ModemVisitor {

	public void visit(HayesModem modem) {
		modem.configurationString = "&s1=4&D=3";

	}

}

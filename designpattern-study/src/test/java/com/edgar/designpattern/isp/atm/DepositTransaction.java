package com.edgar.designpattern.isp.atm;

import com.edgar.designpattern.isp.atm.ui.DepositUI;

public class DepositTransaction implements Transaction {

	private DepositUI depositUI;
	
	private DepositTransaction(DepositUI depositUI) {
		super();
		this.depositUI = depositUI;
	}

	@Override
	public void execute() {
		depositUI.requestDepositAmount();
	}

}

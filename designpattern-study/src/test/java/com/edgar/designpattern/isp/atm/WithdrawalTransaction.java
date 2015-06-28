package com.edgar.designpattern.isp.atm;

import com.edgar.designpattern.isp.atm.ui.WithdraUI;

public class WithdrawalTransaction implements Transaction {

	private WithdraUI withdraUI;
	
	private WithdrawalTransaction(WithdraUI withdraUI) {
		super();
		this.withdraUI = withdraUI;
	}

	@Override
	public void execute() {
		withdraUI.requestWithDrawalAmount();
	}

}

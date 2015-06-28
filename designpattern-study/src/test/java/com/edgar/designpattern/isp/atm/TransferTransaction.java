package com.edgar.designpattern.isp.atm;

import com.edgar.designpattern.isp.atm.ui.TransferUI;

public class TransferTransaction implements Transaction {

	private TransferUI transferUI;
	
	private TransferTransaction(TransferUI transferUI) {
		super();
		this.transferUI = transferUI;
	}

	@Override
	public void execute() {
		transferUI.requestTransferAmount();
	}

}

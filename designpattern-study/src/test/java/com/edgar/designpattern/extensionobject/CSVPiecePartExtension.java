package com.edgar.designpattern.extensionobject;

public class CSVPiecePartExtension implements CSVPartExtension {

	private PiecePart part;

	public CSVPiecePartExtension(PiecePart part) {
		super();
		this.part = part;
	}

	@Override
	public String getCSV() {
		// TODO Auto-generated method stub
		return null;
	}
}

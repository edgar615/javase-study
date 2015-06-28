package com.edgar.designpattern.extensionobject;

public class PiecePart extends Part {

	private String partNumber;

	private String description;
	
	private double cost;

	public PiecePart(String partNumber, String description, double cost) {
		super();
		this.partNumber = partNumber;
		this.description = description;
		this.cost = cost;
		addExtension("CSV", new CSVPiecePartExtension(this));
//		addExtension("XML", new XMLPiecePartExtension(this));
	}

	@Override
	String getPartNumber() {
		return partNumber;
	}

	@Override
	String getDescription() {
		return description;
	}

}

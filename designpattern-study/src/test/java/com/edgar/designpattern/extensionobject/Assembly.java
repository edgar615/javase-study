package com.edgar.designpattern.extensionobject;

import java.util.LinkedList;
import java.util.List;

public class Assembly extends Part {

	private final List<Part> parts = new LinkedList<Part>();

	private String partNumber;

	private String description;

	public Assembly(String partNumber, String description) {
		super();
		this.partNumber = partNumber;
		this.description = description;
		addExtension("CSV", new CSVAssemblyExtension(this));
//		addExtension("XML", new XMLAssemblyExtension(this));
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

package com.edgar.designpattern.extensionobject;

import java.util.HashMap;
import java.util.Map;

public abstract class Part {

	private final Map extensions = new HashMap();

	abstract String getPartNumber();

	abstract String getDescription();

	public void addExtension(String extensionType, PartExtension extension) {
		extensions.put(extensionType, extension);
	}

	public PartExtension getExtension(String extensionType) {
		PartExtension pe = (PartExtension) extensions.get(extensionType);
		if (pe == null) {
			pe = new BadPartExtension();
		}
		return pe;
	}

}

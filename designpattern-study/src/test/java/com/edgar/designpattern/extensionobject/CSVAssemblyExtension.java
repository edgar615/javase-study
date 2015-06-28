package com.edgar.designpattern.extensionobject;

public class CSVAssemblyExtension implements CSVPartExtension {

	private Assembly assembly;

	public CSVAssemblyExtension(Assembly assembly) {
		super();
		this.assembly = assembly;
	}

	@Override
	public String getCSV() {
		// TODO Auto-generated method stub
		return null;
	}
}

package com.edgar.designpattern.visitor.part;

import java.util.HashMap;
import java.util.Map;

public class PartCountVisitor implements PartVisitor {

	private int pieceCount = 0;
	private Map<String, Integer> pieceMap = new HashMap<String, Integer>();

	@Override
	public void visit(Assembly assembly) {

	}

	@Override
	public void visit(PiecePart pricePart) {
		pieceCount++;
		String partNumber = pricePart.getPartNumber();
		int partNumberCount = 0;
		if (pieceMap.containsKey(partNumber)) {
			Integer carrier = pieceMap.get(partNumber);
			partNumberCount = carrier.intValue();
		}
		partNumberCount++;
		pieceMap.put(partNumber, partNumberCount);
	}

	public int getCountForPart(String partNumber) {
		int partNumberCount = 0;
		if (pieceMap.containsKey(partNumber)) {
			Integer carrier = pieceMap.get(partNumber);
			partNumberCount = carrier.intValue();
		}
		return partNumberCount;
	}

}

package com.edgar.designpattern.adapter.duck;

import java.util.Enumeration;
import java.util.Iterator;

public class EnumerationIterator<E> implements Iterator<E> {
	
	Enumeration<E> enumeration;

	public EnumerationIterator(Enumeration<E> enumeration) {
		super();
		this.enumeration = enumeration;
	}

	@Override
	public boolean hasNext() {
		return enumeration.hasMoreElements();
	}

	@Override
	public E next() {
		return enumeration.nextElement();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
		
	}

}

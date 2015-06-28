package com.edgar.designpattern.nullobject;

import java.util.Date;

public interface Employee {

	boolean isTimeToPay(Date payDate);
	
	void pay();
	
	public static final Employee NULL = new Employee() {
		
		@Override
		public void pay() {
			
		}
		
		@Override
		public boolean isTimeToPay(Date payDate) {
			return false;
		}
	};
}

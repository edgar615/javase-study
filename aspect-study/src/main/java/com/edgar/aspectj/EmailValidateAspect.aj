package com.edgar.aspectj;

public aspect EmailValidateAspect {
	declare parents : Email implements Validatable;

	public boolean Email.validateAddress() {
		if (this.getToAddress() != null) {
			return true;
		} else {
			return false;
		}
	}
}

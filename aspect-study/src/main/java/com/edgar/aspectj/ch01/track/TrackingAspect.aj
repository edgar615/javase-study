package com.edgar.aspectj.ch01.track;

import com.edgar.aspectj.ch01.AccessTracked;
import com.edgar.aspectj.ch01.MessageCommunicator;

public aspect TrackingAspect {
	declare parents : MessageCommunicator implements AccessTracked;
	private long AccessTracked.lastAccessedTime;

	public void AccessTracked.updateLastAccessedTime() {
		lastAccessedTime = System.currentTimeMillis();
	}

	public long AccessTracked.getLastAccessedTime() {
		return lastAccessedTime;
	}

	before(AccessTracked accessTracked) : execution(* AccessTracked+.*(..)) 
		&& ! execution(* AccessTracked.*(..)) 
		&& this(accessTracked){
		accessTracked.updateLastAccessedTime();
	}
}

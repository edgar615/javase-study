package com.edgar.aspectj.ch01.security;//package com.edgar.ch01.security;
//
//import com.edgar.ch01.MessageCommunicator;
//
//public aspect SecurityAspect {
//
//	private Authenticator authenticator = new Authenticator();
//
//	pointcut secureAccess() : execution(* MessageCommunicator.deliver(..));
//
//	before() : secureAccess() {
//		System.out.println("Checking and authenticating user");
//		authenticator.authenticate();
//	}
//
//	declare warning : call(void Authenticator.authenticate())
//		&& !within(SecurityAspect)
//		: "Authentication should be performed only by SecurityAspect";
//}

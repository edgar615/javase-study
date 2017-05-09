package com.edgar.aspectj.ch01.security;//package com.edgar.ch01.security;
//
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//
//@Aspect
//public class SecurityAspect2 {
//	private Authenticator authenticator = new Authenticator();
//
//	@Pointcut("execution(* com.edgar.ch01.MessageCommunicator.deliver(..))")
//	public void secureAccess() {
//	}
//
//	@Before("secureAccess()")
//	public void secure() {
//		System.out.println("Checking and authenticating user");
//		authenticator.authenticate();
//	}
//}
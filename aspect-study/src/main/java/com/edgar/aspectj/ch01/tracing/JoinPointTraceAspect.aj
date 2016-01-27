package com.edgar.aspectj.ch01.tracing;//package com.edgar.ch01.tracing;
//
//public aspect JoinPointTraceAspect {
//
//	private int callDepth;
//
//	pointcut traced() : !within(JoinPointTraceAspect);
//
//	before() : traced() {
//		print("Before", thisJoinPoint);
//		callDepth++;
//	}
//
//	after() : traced() {
//		callDepth--;
//		print("After", thisJoinPoint);
//	}
//
//	private void print(String prefix, Object message) {
//		for (int i = 0; i < callDepth; i++) {
//			System.out.print(" ");
//		}
//		System.out.println(prefix + ": " + message);
//	}
//}

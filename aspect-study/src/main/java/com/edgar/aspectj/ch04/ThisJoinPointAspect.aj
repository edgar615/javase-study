package com.edgar.aspectj.ch04;

public aspect ThisJoinPointAspect {
	pointcut callPointCut() : call(* Account.debit(..));

	before() : callPointCut() {
		System.out
				.println("------------------- Aspect Advice Logic --------------------");
		System.out.println("Source Line: "
				+ thisJoinPointStaticPart.getSourceLocation());
		System.out.println("Join Point Kind: "
				+ thisJoinPointStaticPart.getKind());
		System.out.println("Simple toString: "
				+ thisJoinPointStaticPart.toString());
		System.out.println("Simple toShortString: "
				+ thisJoinPointStaticPart.toShortString());
		System.out.println("Simple toLongString: "
				+ thisJoinPointStaticPart.toLongString());
		System.out.println("Signature: "
				+ thisJoinPointStaticPart.getSignature());
		System.out.println("Signature name: "
				+ thisJoinPointStaticPart.getSignature().getName());
		System.out.println("Signature declaring type: "
				+ thisJoinPointStaticPart.getSignature().getDeclaringType());
		System.out
				.println("------------------------------------------------------------");

	}

	before() : callPointCut( ) {

		System.out
				.println("------------------- Aspect Advice Logic --------------------");
		System.out
				.println("Get the this reference: " + thisJoinPoint.getThis());
		System.out.println("Getting the Target: " + thisJoinPoint.getTarget());
		System.out.println("Join Point Arguments: ");
		Object[] args = thisJoinPoint.getArgs();
		for (int count = 0; count < args.length; count++) {
			System.out.println(args[count]);
		}
		System.out
				.println("------------------------------------------------------------");
	}
}

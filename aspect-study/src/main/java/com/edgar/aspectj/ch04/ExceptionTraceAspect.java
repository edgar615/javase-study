package com.edgar.aspectj.ch04;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ExceptionTraceAspect {

	private Logger logger = Logger.getLogger("ExceptionTraceAspect");

	private ThreadLocal<Throwable> lastLoggedException = new ThreadLocal<Throwable>();

	@Pointcut("execution(* com.edgar..*.*(..)) &&"
			+ "!within(ExceptionTraceAspect)")
	public void exceptionTraced() {
	}

	@AfterThrowing(pointcut = "exceptionTraced()", throwing = "ex")
	public void afterExceptionTraced(JoinPoint jp, Throwable ex) {
		if (lastLoggedException.get() != ex) {
			lastLoggedException.set(ex);
			Signature sig = jp.getSignature();
			logger.log(Level.SEVERE,
					"Exception trace aspect [" + sig.toShortString() + "]", ex);
		}
	}
}
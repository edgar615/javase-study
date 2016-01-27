package com.edgar.aspectj.ch04;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect  
public class ArgsExample {  

    @Pointcut("call(void com.edgar.aspectj.ch04.Account+.debit(..)) && args(amount,..)")  
    public void argsPointcut(double amount) {  
    }  

    @Before("argsPointcut(amount)")  
    public void beforeArgsPointcut(double amount) {  
        System.out.println("argsPointcut " + amount);  
    }  

//    @Pointcut("call(* *.*(..))")  
//    public void anyPublicMethod() {  
//    }  
//
//    @Before("anyPublicMethod() && " +  
//            "@annotation(auditable)")  
//    public void audit(Auditable auditable) {  
//        AuditCode code = auditable.value();  
//        System.out.println(code);  
//    }  

}  
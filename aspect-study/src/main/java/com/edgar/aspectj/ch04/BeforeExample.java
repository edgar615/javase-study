package com.edgar.aspectj.ch04;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect  
public class BeforeExample {  

    @Pointcut("call(void com.edgar.aspectj.ch04.Account+.debit(..))")  
    public void beforeDebitPointcut(){}  
      
    @Before("beforeDebitPointcut()")  
    public void beforeDebit() {  
        System.out.println("beforeDebitPointcut()");  
    }  
}  
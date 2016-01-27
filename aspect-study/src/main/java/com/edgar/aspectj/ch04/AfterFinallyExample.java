package com.edgar.aspectj.ch04;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect  
public class AfterFinallyExample {  
  
    @Pointcut("call(void com.edgar.aspectj.ch04.Account+.debit(..))")  
    public void afterPointCut(){}  
      
    @After("afterPointCut()")  
    public void after() {  
        System.out.println("after");  
    }  
}
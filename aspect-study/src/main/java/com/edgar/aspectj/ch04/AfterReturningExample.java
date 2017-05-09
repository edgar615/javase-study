package com.edgar.aspectj.ch04;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect  
public class AfterReturningExample {  

    @Pointcut("get(float com.edgar.aspectj.ch04.Account+.balance)")  
    public void afterReturningPointCut(){}  
      
    @AfterReturning("afterReturningPointCut()")  
    public void afterRetring() {  
        System.out.println("afterRetring()");  
    }  
      
    @AfterReturning(pointcut="afterReturningPointCut()",  
            returning="retVal")  
    public void afterRetring(Object retVal) {  
        System.out.println(retVal);  
    }  
}  
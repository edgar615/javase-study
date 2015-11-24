package com.edgar.powermockito.teststatic;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
 //We prepare the IdGenerator for test because the static method is normally not mockable 
 @PrepareForTest(IdGenerator.class)
 public class MyTestClass { 
    @Test
    public void demoStaticMethodMocking() throws Exception { 
        mockStatic(IdGenerator.class); 
        /* 
         * Setup the expectation using the standard Mockito syntax, 
         * generateNewId() will now return 2 everytime it's invoked 
         * in this test. 
         */ 
        when(IdGenerator.generateNewId()).thenReturn(2L); 

        new ClassUnderTest().methodToTest(); 

        // Optionally verify that the static method was actually called 
        verifyStatic(); 
        IdGenerator.generateNewId(); 
    } 
 }
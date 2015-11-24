package com.edgar.powermockito.testfinal;

public class ClassUnderTest {

  public boolean callFinalMethod(ClassDependency refer) {

    return refer.isAlive();

  }
}
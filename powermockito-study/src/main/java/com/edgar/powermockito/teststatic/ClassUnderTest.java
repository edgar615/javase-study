package com.edgar.powermockito.teststatic;

public class ClassUnderTest {
  public void methodToTest() {
    final long id = IdGenerator.generateNewId();
  }
}
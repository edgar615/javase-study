package com.edgar.powermockito.testprivate;

public final class PrivatePartialMockingExample {
    public String methodToTest() {
        return methodToMock("input");
    }

    private String methodToMock(String input) {
        return "REAL VALUE = " + input;
    }
 }
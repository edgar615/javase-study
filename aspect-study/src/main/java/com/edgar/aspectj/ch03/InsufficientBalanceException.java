package com.edgar.aspectj.ch03;
public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }    
}
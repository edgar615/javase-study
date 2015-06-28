package com.edgar.core.exception.example2;

public interface ExceptionHandler{

   public void handle(String context, int code,
                      String message, Throwable t);

   public void raise(String context, int code,
                     String message);
   
}
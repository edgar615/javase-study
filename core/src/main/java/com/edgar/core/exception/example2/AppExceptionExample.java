package com.edgar.core.exception.example2;

/**
 * Created by edgar on 15-6-28.
 */
public class AppExceptionExample {

    public static void main(String[] args) {
        throw new AppException("service", AppErrorCode.NULL, "eror");
    }
}

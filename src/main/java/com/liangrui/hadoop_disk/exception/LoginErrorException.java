package com.liangrui.hadoop_disk.exception;

/**
 * 登录异常信息
 */
public class LoginErrorException extends RuntimeException {

    public LoginErrorException() {
    }

    public LoginErrorException(String message) {
        super(message);
    }
}

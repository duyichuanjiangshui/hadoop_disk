package com.liangrui.hadoop_disk.exception;

/**
 * 对象未找到异常
 */
public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String message) {
        super(message);
    }
}

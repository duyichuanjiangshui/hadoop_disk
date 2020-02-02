package com.liangrui.hadoop_disk.exception;

public class ObjectAlreadyExistException extends RuntimeException {
    public ObjectAlreadyExistException() {
    }

    public ObjectAlreadyExistException(String message) {
        super(message);
    }
}

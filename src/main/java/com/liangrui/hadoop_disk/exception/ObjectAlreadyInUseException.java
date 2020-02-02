package com.liangrui.hadoop_disk.exception;

public class ObjectAlreadyInUseException extends RuntimeException {
    public ObjectAlreadyInUseException() {
    }

    public ObjectAlreadyInUseException(String message) {
        super(message);
    }
}

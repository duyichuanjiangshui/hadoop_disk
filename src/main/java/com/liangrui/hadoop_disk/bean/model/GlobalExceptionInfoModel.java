package com.liangrui.hadoop_disk.bean.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class GlobalExceptionInfoModel<T> implements Serializable {

    public static final Integer OK = 0;
    public static final Integer ERROR = 100;
    private static final long serialVersionUID = -6937722414645880868L;

    private Integer code;
    private String msg;
    private T data;
    private String uri;
}

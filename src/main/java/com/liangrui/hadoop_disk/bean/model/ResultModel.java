package com.liangrui.hadoop_disk.bean.model;

import java.io.Serializable;

/**
 * Controller响应的结果对象
 *
 * @param <T> 响应的数据类型
 */
public class ResultModel<T> implements Serializable {
    /**
     * 响应结果状态码：0：success，1：error
     */
    private Integer code;
    /**
     * 响应的提示信息
     */
    private String message;
    /**
     * 响应的结果数据
     */
    private T result;

    public ResultModel() {

    }

    public ResultModel(Integer code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
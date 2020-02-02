package com.liangrui.hadoop_disk.bean.model;

import java.io.Serializable;

public class PageModel<T> implements Serializable {
    private Integer page;
    private Integer limit;
    private Integer count;
    private Integer number;
    private T data;

    public PageModel() {
    }

    public PageModel(T data) {
        this.data = data;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

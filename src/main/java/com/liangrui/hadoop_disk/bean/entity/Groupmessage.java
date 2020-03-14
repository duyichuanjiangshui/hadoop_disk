package com.liangrui.hadoop_disk.bean.entity;

public class Groupmessage {
    private Integer id;

    private Integer formid;

    private String message;

    private String time;

    private Integer groupid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFormid() {
        return formid;
    }

    public void setFormid(Integer formid) {
        this.formid = formid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }
}
package com.liangrui.hadoop_disk.bean.entity;

public class Usermessage {
    private Integer id;

    private Integer statue;

    private Integer touserid;

    private Integer fromuserid;

    private String time;

    private String message;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatue() {
        return statue;
    }

    public void setStatue(Integer statue) {
        this.statue = statue;
    }

    public Integer getTouserid() {
        return touserid;
    }

    public void setTouserid(Integer touserid) {
        this.touserid = touserid;
    }

    public Integer getFromuserid() {
        return fromuserid;
    }

    public void setFromuserid(Integer fromuserid) {
        this.fromuserid = fromuserid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }
}
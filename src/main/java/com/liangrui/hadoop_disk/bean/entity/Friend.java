package com.liangrui.hadoop_disk.bean.entity;

public class Friend {
    private Integer id;

    private Integer masterid;

    private Integer friendid;

    private String establishtime;

    private String comment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMasterid() {
        return masterid;
    }

    public void setMasterid(Integer masterid) {
        this.masterid = masterid;
    }

    public Integer getFriendid() {
        return friendid;
    }

    public void setFriendid(Integer friendid) {
        this.friendid = friendid;
    }

    public String getEstablishtime() {
        return establishtime;
    }

    public void setEstablishtime(String establishtime) {
        this.establishtime = establishtime == null ? null : establishtime.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}
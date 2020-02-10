package com.liangrui.hadoop_disk.bean.entity;

public class Groupnumber {
    private Integer id;

    private Integer groupid;

    private Integer userid;

    private String jointime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getJointime() {
        return jointime;
    }

    public void setJointime(String jointime) {
        this.jointime = jointime == null ? null : jointime.trim();
    }
}
package com.liangrui.hadoop_disk.bean.entity;

public class Upload {
    private Integer uploadloationid;

    private String uploadlocation;

    private String uploadtime;

    private Integer userid;

    private String orignalname;

    private Integer uploadtype;

    private Integer usernum;

    public Integer getUploadloationid() {
        return uploadloationid;
    }

    public void setUploadloationid(Integer uploadloationid) {
        this.uploadloationid = uploadloationid;
    }

    public String getUploadlocation() {
        return uploadlocation;
    }

    public void setUploadlocation(String uploadlocation) {
        this.uploadlocation = uploadlocation == null ? null : uploadlocation.trim();
    }

    public String getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(String uploadtime) {
        this.uploadtime = uploadtime == null ? null : uploadtime.trim();
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getOrignalname() {
        return orignalname;
    }

    public void setOrignalname(String orignalname) {
        this.orignalname = orignalname == null ? null : orignalname.trim();
    }

    public Integer getUploadtype() {
        return uploadtype;
    }

    public void setUploadtype(Integer uploadtype) {
        this.uploadtype = uploadtype;
    }

    public Integer getUsernum() {
        return usernum;
    }

    public void setUsernum(Integer usernum) {
        this.usernum = usernum;
    }
}
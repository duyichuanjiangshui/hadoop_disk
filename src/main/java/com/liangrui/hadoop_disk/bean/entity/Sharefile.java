package com.liangrui.hadoop_disk.bean.entity;

public class Sharefile {
    private Integer shareid;

    private Integer userid;

    private String sharetime;

    private String password;

    private String starttime;

    private String shareurl;

    private Integer statue;

    public Integer getShareid() {
        return shareid;
    }

    public void setShareid(Integer shareid) {
        this.shareid = shareid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getSharetime() {
        return sharetime;
    }

    public void setSharetime(String sharetime) {
        this.sharetime = sharetime == null ? null : sharetime.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime == null ? null : starttime.trim();
    }

    public String getShareurl() {
        return shareurl;
    }

    public void setShareurl(String shareurl) {
        this.shareurl = shareurl == null ? null : shareurl.trim();
    }

    public Integer getStatue() {
        return statue;
    }

    public void setStatue(Integer statue) {
        this.statue = statue;
    }
}
package com.liangrui.hadoop_disk.bean.model;

public class MyShareModel {

    private int viewshareNumber;
    private String shareurl;
    private String password;
    private String starttime;
    private String sharetime;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getViewshareNumber() {
        return viewshareNumber;
    }

    public void setViewshareNumber(int viewshareNumber) {
        this.viewshareNumber = viewshareNumber;
    }

    public String getShareurl() {
        return shareurl;
    }

    public void setShareurl(String shareurl) {
        this.shareurl = shareurl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getSharetime() {
        return sharetime;
    }

    public void setSharetime(String sharetime) {
        this.sharetime = sharetime;
    }
}

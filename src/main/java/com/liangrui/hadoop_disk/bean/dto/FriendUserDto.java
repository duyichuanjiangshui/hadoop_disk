package com.liangrui.hadoop_disk.bean.dto;

public class FriendUserDto {
    private int friendid;
    private String imgsrc;
    private int haveNoRead;
    private String name;
    private String sign;
    private int statue;

    public int getStatue() {
        return statue;
    }

    public void setStatue(int statue) {
        this.statue = statue;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFriendid() {
        return friendid;
    }

    public void setFriendid(int friendid) {
        this.friendid = friendid;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public int getHaveNoRead() {
        return haveNoRead;
    }

    public void setHaveNoRead(int haveNoRead) {
        this.haveNoRead = haveNoRead;
    }
}

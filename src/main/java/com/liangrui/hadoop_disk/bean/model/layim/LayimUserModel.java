package com.liangrui.hadoop_disk.bean.model.layim;

public class LayimUserModel {
    /*
    * json 格式
    *  "username": "纸飞机"
      ,"id": "100000"
      ,"status": "online"
      ,"sign": "在深邃的编码世界，做一枚轻盈的纸飞机"
      ,"avatar": "http://cdn.firstlinkapp.com/upload/2016_6/1465575923433_33812.jpg"
    * */

    private String username;
    private int id;
    private String status;
    private String sign;
    private String avatar;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

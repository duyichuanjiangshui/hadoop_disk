package com.liangrui.hadoop_disk.bean.model.layim;

public class LayimGroupModel {
    /*
    "groupname": "前端群"
      ,"id": "101"
      ,"avatar": "http://tp2.sinaimg.cn/2211874245/180/40050524279/0"
      */
    private String groupname;
    private int id;
    private String avatar;

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

package com.liangrui.hadoop_disk.bean.model.layim;

import java.util.List;

public class LayimFriendSMoldel {
    /*
    * {
      "groupname": "网红"
      ,"id": 2
      ,"online": 3
      ,"list": []
    * */
    private String groupname;
    private int id;
    private int online;
    private List<LayimUserModel> list;

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

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public List<LayimUserModel> getList() {
        return list;
    }

    public void setList(List<LayimUserModel> list) {
        this.list = list;
    }
}

package com.liangrui.hadoop_disk.bean.dto;

import com.liangrui.hadoop_disk.bean.entity.Groupnumber;

import java.util.List;

public class GroupDto {
    private int groupid;
    private String imgsrc;
    private String name;
    private int isowner;
    private String rootfolder;



    public int getIsowner() {
        return isowner;
    }

    public void setIsowner(int isowner) {
        this.isowner = isowner;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRootfolder() {
        return rootfolder;
    }

    public void setRootfolder(String rootfolder) {
        this.rootfolder = rootfolder;
    }
}

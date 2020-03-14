package com.liangrui.hadoop_disk.bean.dto;

public class FileAndFolderDto {
    private String id;
    private String name;
    private String updatetime;
    private float size;
    private String filetype;
    private int type;
    private int sharetype;
    private String fatherfoldername;
    private String fatherfolderid;
    private String deltetime;
    private int userid;
    private  String username;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeltetime() {
        return deltetime;
    }

    public void setDeltetime(String deltetime) {
        this.deltetime = deltetime;
    }

    public String getFatherfolderid() {
        return fatherfolderid;
    }

    public void setFatherfolderid(String fatherfolderid) {
        this.fatherfolderid = fatherfolderid;
    }

    public String getFatherfoldername() {
        return fatherfoldername;
    }

    public void setFatherfoldername(String fatherfoldername) {
        this.fatherfoldername = fatherfoldername;
    }

    public int getSharetype() {
        return sharetype;
    }

    public void setSharetype(int sharetype) {
        this.sharetype = sharetype;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }



}

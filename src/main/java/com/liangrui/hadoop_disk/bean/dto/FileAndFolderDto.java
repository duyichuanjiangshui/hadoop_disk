package com.liangrui.hadoop_disk.bean.dto;

public class FileAndFolderDto {
    private int id;
    private String name;
    private String updatetime;
    private float size;
    private String filetype;
    private int type;
    private int sharetype;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

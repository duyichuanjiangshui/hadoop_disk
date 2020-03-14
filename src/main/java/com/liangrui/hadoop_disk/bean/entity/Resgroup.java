package com.liangrui.hadoop_disk.bean.entity;

public class Resgroup {
    private Integer groupid;

    private String name;

    private String createtime;

    private String imgpath;

    private String descripe;

    private Integer userid;

    private String rootfolder;

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath == null ? null : imgpath.trim();
    }

    public String getDescripe() {
        return descripe;
    }

    public void setDescripe(String descripe) {
        this.descripe = descripe == null ? null : descripe.trim();
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getRootfolder() {
        return rootfolder;
    }

    public void setRootfolder(String rootfolder) {
        this.rootfolder = rootfolder == null ? null : rootfolder.trim();
    }
}
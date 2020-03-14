package com.liangrui.hadoop_disk.bean.entity;

public class Folder {
    private String folderid;

    private String fatherfolderid;

    private Integer groupid;

    private String name;

    private String updatetime;

    private Integer userid;

    private String createtime;

    private Integer sharetype;

    private Integer isdelete;

    private String deletetime;

    public String getFolderid() {
        return folderid;
    }

    public void setFolderid(String folderid) {
        this.folderid = folderid == null ? null : folderid.trim();
    }

    public String getFatherfolderid() {
        return fatherfolderid;
    }

    public void setFatherfolderid(String fatherfolderid) {
        this.fatherfolderid = fatherfolderid == null ? null : fatherfolderid.trim();
    }

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

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime == null ? null : updatetime.trim();
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public Integer getSharetype() {
        return sharetype;
    }

    public void setSharetype(Integer sharetype) {
        this.sharetype = sharetype;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    public String getDeletetime() {
        return deletetime;
    }

    public void setDeletetime(String deletetime) {
        this.deletetime = deletetime == null ? null : deletetime.trim();
    }
}
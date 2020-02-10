package com.liangrui.hadoop_disk.bean.entity;

public class Folder {
    private Integer folderid;

    private Integer fatherfolderid;

    private String name;

    private String updatetime;

    private Integer userid;

    private String createtime;

    private Integer sharetype;

    private Integer isdelete;

    public Integer getFolderid() {
        return folderid;
    }

    public void setFolderid(Integer folderid) {
        this.folderid = folderid;
    }

    public Integer getFatherfolderid() {
        return fatherfolderid;
    }

    public void setFatherfolderid(Integer fatherfolderid) {
        this.fatherfolderid = fatherfolderid;
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
}
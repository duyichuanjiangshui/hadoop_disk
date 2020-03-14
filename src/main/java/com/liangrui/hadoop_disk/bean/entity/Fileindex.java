package com.liangrui.hadoop_disk.bean.entity;

public class Fileindex {
    private Integer fileid;

    private String fatherfolderid;

    private Integer groupid;

    private Integer uploadlocationid;

    private Float size;

    private String name;

    private String updatetime;

    private String uploadtime;

    private Integer userid;

    private String deletetime;

    private Integer sharetype;

    private Integer savenum;

    private String filetype;

    private Integer sortype;

    private Integer isdelete;

    public Integer getFileid() {
        return fileid;
    }

    public void setFileid(Integer fileid) {
        this.fileid = fileid;
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

    public Integer getUploadlocationid() {
        return uploadlocationid;
    }

    public void setUploadlocationid(Integer uploadlocationid) {
        this.uploadlocationid = uploadlocationid;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
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

    public String getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(String uploadtime) {
        this.uploadtime = uploadtime == null ? null : uploadtime.trim();
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getDeletetime() {
        return deletetime;
    }

    public void setDeletetime(String deletetime) {
        this.deletetime = deletetime == null ? null : deletetime.trim();
    }

    public Integer getSharetype() {
        return sharetype;
    }

    public void setSharetype(Integer sharetype) {
        this.sharetype = sharetype;
    }

    public Integer getSavenum() {
        return savenum;
    }

    public void setSavenum(Integer savenum) {
        this.savenum = savenum;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype == null ? null : filetype.trim();
    }

    public Integer getSortype() {
        return sortype;
    }

    public void setSortype(Integer sortype) {
        this.sortype = sortype;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }
}
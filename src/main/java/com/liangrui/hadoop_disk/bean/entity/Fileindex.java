package com.liangrui.hadoop_disk.bean.entity;

public class Fileindex {
    private Integer fileid;

    private Integer fatherfolder;

    private Integer uploadlocationid;

    private Float size;

    private String name;

    private String updatetime;

    private String uploadtime;

    private Integer userid;

    private Integer sharetype;

    private Integer savenum;

    private String filetype;

    private Integer isdelete;

    public Integer getFileid() {
        return fileid;
    }

    public void setFileid(Integer fileid) {
        this.fileid = fileid;
    }

    public Integer getFatherfolder() {
        return fatherfolder;
    }

    public void setFatherfolder(Integer fatherfolder) {
        this.fatherfolder = fatherfolder;
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

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }
}
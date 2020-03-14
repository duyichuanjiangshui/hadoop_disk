package com.liangrui.hadoop_disk.bean.entity;

public class Sharedetail {
    private Integer sharedetailid;

    private Integer shareid;

    private Integer resourcetype;

    private String resourcetypeid;

    public Integer getSharedetailid() {
        return sharedetailid;
    }

    public void setSharedetailid(Integer sharedetailid) {
        this.sharedetailid = sharedetailid;
    }

    public Integer getShareid() {
        return shareid;
    }

    public void setShareid(Integer shareid) {
        this.shareid = shareid;
    }

    public Integer getResourcetype() {
        return resourcetype;
    }

    public void setResourcetype(Integer resourcetype) {
        this.resourcetype = resourcetype;
    }

    public String getResourcetypeid() {
        return resourcetypeid;
    }

    public void setResourcetypeid(String resourcetypeid) {
        this.resourcetypeid = resourcetypeid == null ? null : resourcetypeid.trim();
    }
}
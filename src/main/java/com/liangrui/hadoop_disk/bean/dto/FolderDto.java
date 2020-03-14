package com.liangrui.hadoop_disk.bean.dto;

import com.liangrui.hadoop_disk.bean.entity.Folder;

import java.util.List;

public class FolderDto extends Folder {
    // 表示权限的下一级菜单
    private List<FolderDto> children;
    // 表示权限的上一级菜单
    private Folder parent;
    // 表示管理页面下面的权限
    public FolderDto()
    {

    }
    public FolderDto(Folder folder)
    {
        this.setFatherfolderid(folder.getFatherfolderid());
        this.setFolderid(folder.getFolderid());
        this.setName(folder.getName());
        this.setIsdelete(folder.getIsdelete());
        this.setSharetype(folder.getSharetype());
        this.setUpdatetime(folder.getUpdatetime());
        this.setCreatetime(folder.getCreatetime());
        this.setUserid(folder.getUserid());
        this.setParent(parent);
        this.setChildren(children);
    }


    public List<FolderDto> getChildren() {
        return children;
    }

    public void setChildren(List<FolderDto> children) {
        this.children = children;
    }

    public Folder getParent() {
        return parent;
    }

    public void setParent(Folder parent) {
        this.parent = parent;
    }
}

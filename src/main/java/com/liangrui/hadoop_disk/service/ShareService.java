package com.liangrui.hadoop_disk.service;

import com.liangrui.hadoop_disk.bean.dto.FileAndFolderDto;
import com.liangrui.hadoop_disk.bean.dto.FolderDto;
import com.liangrui.hadoop_disk.bean.entity.Resgroup;
import com.liangrui.hadoop_disk.bean.entity.Sharefile;
import com.liangrui.hadoop_disk.bean.model.MyShareModel;

import java.util.List;

public interface ShareService {
    public void addShare(List<FileAndFolderDto> list, Sharefile sharefile);
    Resgroup findbygroupid(int groupid);
    List<FolderDto> getFolderTree(int groupid);
    void shareinGroup(List<FileAndFolderDto> list ,int groupid, String aimFolderid);
    Sharefile getfilebyShareurl(String shareurl);
    int sharefileIsLowNewdate(String datestring,String day);
    List<FileAndFolderDto> getsharedetail( String shareUrl);
    List<MyShareModel> getMyShare(int userid);
    int delete(int id);
    void deleteall(List<MyShareModel> list);
}

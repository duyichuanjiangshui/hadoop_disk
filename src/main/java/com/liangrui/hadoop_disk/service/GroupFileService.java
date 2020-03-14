package com.liangrui.hadoop_disk.service;

import com.liangrui.hadoop_disk.bean.dto.FileAndFolderDto;
import com.liangrui.hadoop_disk.bean.entity.Sharefile;

import java.util.List;

public interface GroupFileService {
    public List<FileAndFolderDto> findAllFileAndFolder(String fatherFolderid);
    public int addFolder(String filename, String fatherFolderid, int userid,int groupid);
    public List<FileAndFolderDto> searchbyname(int groupid,String text);
    public List<FileAndFolderDto> searchGroupfileByUserid(int groupid,int userid);
    public int deleteGroupFile(String id,int type);
    boolean isexitNmaeinGrouplist(List<FileAndFolderDto> list, int groupid, String aimFolderid);
    int copyFolder(String folderid, String aimFolderid, int groupid);
   int copyFile(String folderid, String aimFolderid, int groupid);
}

package com.liangrui.hadoop_disk.service;

import com.liangrui.hadoop_disk.bean.dto.FileAndFolderDto;
import com.liangrui.hadoop_disk.bean.dto.FolderDto;

import java.util.List;

public interface FileAndFolderService {
    List<FileAndFolderDto> findAllFileAndFolder(int fatherFolderid,int userid);
    int addFolder(String filename,int fatherFolderid,int userid);
    boolean isexist(String foldername, int userid,int fatherFolderid);
    int delteFolder(int folderid);
    int updateFoler(String foldername, int folderid);
    int changesharetype(int sharetype,int type,int fileid);
    boolean isexistfile(String filename, int userid,int fatherFolderid);
    int updateFileName(String filname, int fileid);
    int deleteFile(int fileid);
    List<FolderDto> getFolderTree(int userid);
    int copyFolder(int folderid ,int aimFolderid,int userid);
    int copyFile(int folderid ,int aimFolderid,int userid);
    int moverFileAndFolder(int folderid ,int type,int aimFolderid);
    //判断文件夹是否是其目的文件夹的本身或者夫文件夹
    boolean isfatherFolder(int folderid , int aimFolderid, int userid);
    boolean canmoveorcopy(int folderid ,int type, int altertype,int aimFolderid,int userid);
    boolean allcanmoveorcopy(List<FileAndFolderDto> list,int userid,int aimFolderid);
    boolean isexitMoveOrCopyNmae(int folderid ,int type, int altertype,int aimFolderid,int userid);
    boolean isexitMoveOrCopyNmaeinlist(List<FileAndFolderDto> list,int userid,int aimFolderid, int altertype);
}

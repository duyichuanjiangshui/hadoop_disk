package com.liangrui.hadoop_disk.service;

import com.liangrui.hadoop_disk.bean.dto.FileAndFolderDto;
import com.liangrui.hadoop_disk.bean.dto.FolderDto;

import java.util.List;

public interface FileAndFolderService {
    List<FileAndFolderDto> findAllFileAndFolder(String fatherFolderid);
    List<FileAndFolderDto> findAllIsOrNoDeleteFileAndFolder(String fatherFolderid);
    int addFolder(String filename, String fatherFolderid, int userid);
    boolean isexist(String foldername, String fatherFolderid);
    int delteFolder(String folderid);
    int updateFoler(String foldername, String folderid);
    int changesharetype(int sharetype, int type, String fileid);
    boolean isexistfile(String filename, String fatherFolderid);
    int updateFileName(String filname, int fileid);
    int deleteFile(int fileid);
    List<FolderDto> getFolderTree(int userid);
    int copyFolder(String folderid , String aimFolderid, int userid);
    int copyFile(String folderid , String aimFolderid, int userid);
    int moverFileAndFolder(String folderid , int type, String aimFolderid);
    //判断文件夹是否是其目的文件夹的本身或者夫文件夹
    boolean isfatherFolder(String folderid , String aimFolderid, int userid);
    boolean canmoveorcopy(String folderid , int type, int altertype, String aimFolderid, int userid);
    boolean allcanmoveorcopy(List<FileAndFolderDto> list, int userid, String aimFolderid);
    boolean isexitMoveOrCopyNmae(String folderid , int type, String aimFolderid, int userid);
    boolean isexitMoveOrCopyNmaeinlist(List<FileAndFolderDto> list, int userid, String aimFolderid);
     List<FileAndFolderDto> findByLikeName(int userid,String name,String rootFolderid);
    List<FileAndFolderDto> findByFiltype(int userid,int type,String rootFolderid);
    List<FileAndFolderDto> findalonefileByFiltype(int userid,int type,String name,String rootname);
    public List<FileAndFolderDto> findrecyclerfile(int userid,String rootfolderid);
    int delterecycle(int userid, String fileOrFolderid, int type);//是文件还是文件夹
    int recycle(int userid, String folderid, int type);
    String getrootFolder(int userid);
    //搜索
    List<FileAndFolderDto> searchpublic(String text,List<Integer> integers);
     List<FileAndFolderDto> findPublicAllFileAndFolder(String fatherFolderid);
    int delrepatUploadid(int userid);
    int delgarbagefile(int userid);
    int delemptyfolder(int userid);
}

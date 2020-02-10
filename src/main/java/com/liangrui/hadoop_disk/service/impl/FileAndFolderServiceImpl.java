package com.liangrui.hadoop_disk.service.impl;

import com.liangrui.hadoop_disk.bean.dto.FileAndFolderDto;
import com.liangrui.hadoop_disk.bean.dto.FolderDto;
import com.liangrui.hadoop_disk.bean.entity.Fileindex;
import com.liangrui.hadoop_disk.bean.entity.Folder;
import com.liangrui.hadoop_disk.mapper.FileindexMapper;
import com.liangrui.hadoop_disk.mapper.FolderMapper;
import com.liangrui.hadoop_disk.service.FileAndFolderService;
import com.liangrui.hadoop_disk.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileAndFolderServiceImpl implements FileAndFolderService {
    @Autowired
    private FolderMapper folderMapper;
    @Autowired
    private FileindexMapper fileindexMapper;
    @Override
    public List<FileAndFolderDto> findAllFileAndFolder(int fatherFolderid,int userid) {
        List<Folder> list=folderMapper.selectByFatherFolder(fatherFolderid,userid);
        List<Fileindex> list1=fileindexMapper.selectByFatherFolder(fatherFolderid,userid);
        List<FileAndFolderDto> list2 = new ArrayList<>();
        for(Folder folder:list)
        {
            FileAndFolderDto fileAndFolderDto=new FileAndFolderDto();
            fileAndFolderDto.setId(folder.getFolderid());
            fileAndFolderDto.setName(folder.getName());
            fileAndFolderDto.setSharetype(folder.getSharetype());
            fileAndFolderDto.setUpdatetime(folder.getUpdatetime());
            list2.add(fileAndFolderDto);
        }
        for(Fileindex fileindex:list1)
        {
            FileAndFolderDto fileAndFolderDto=new FileAndFolderDto();
            fileAndFolderDto.setId(fileindex.getFileid());
            fileAndFolderDto.setName(fileindex.getName());
            fileAndFolderDto.setUpdatetime(fileindex.getUpdatetime());
            fileAndFolderDto.setType(1);
            fileAndFolderDto.setSize(fileindex.getSize());
            fileAndFolderDto.setFiletype(fileindex.getFiletype());
            fileAndFolderDto.setSharetype(fileindex.getSharetype());
            list2.add(fileAndFolderDto);
        }

        return list2;
    }

    @Override
    public int addFolder(String filename, int fatherFolderid, int userid) {
        Folder folder=new Folder();
        folder.setCreatetime(DateUtil.DateToString("yyyy-MM-dd HH:mm:ss", new Date()));
        folder.setFatherfolderid(fatherFolderid);
        folder.setUpdatetime(DateUtil.DateToString("yyyy-MM-dd HH:mm:ss", new Date()));
        folder.setName(filename);
        folder.setSharetype(0);
        folder.setUserid(userid);
        folder.setIsdelete(0);
        return folderMapper.insert(folder);
    }

    @Override
    public boolean isexist(String foldername, int userid,int fatherFolderid) {
        Folder folder;
        System.out.println(foldername+userid);
        folder=folderMapper.selectByname(userid,foldername,fatherFolderid);
        return folder!=null;
    }

    @Override
    public int delteFolder(int folderid) {
       //删除其实是伪善除
        Folder folder=folderMapper.selectByPrimaryKey(folderid);
        folder.setIsdelete(1);
        return folderMapper.updateByPrimaryKey(folder);
    }

    @Override
    public int updateFoler(String foldername, int folderid) {
        Folder folder=folderMapper.selectByPrimaryKey(folderid);
        folder.setUpdatetime(DateUtil.DateToString("yyyy-MM-dd HH:mm:ss", new Date()));
        folder.setName(foldername);
        return folderMapper.updateByPrimaryKey(folder);
    }

    @Override
    public int changesharetype(int sharetype, int type, int fileid) {
        if(type==0)
        {
            Folder folder=folderMapper.selectByPrimaryKey(fileid);
            folder.setSharetype(sharetype);
            return folderMapper.updateByPrimaryKey(folder);
        }else{
            Fileindex fileindex=fileindexMapper.selectByPrimaryKey(fileid);
            fileindex.setSharetype(sharetype);
            return fileindexMapper.updateByPrimaryKey(fileindex);
        }
    }

    @Override
    public boolean isexistfile(String filename, int userid,int fatherFolderid) {
        Fileindex fileindex;
        fileindex=fileindexMapper.selectByname(userid,filename,fatherFolderid);
        return fileindex!=null;
    }
    public int updateFileName(String filname, int fileid) {
        Fileindex fileindex=fileindexMapper.selectByPrimaryKey(fileid);
        fileindex.setUpdatetime(DateUtil.DateToString("yyyy-MM-dd HH:mm:ss", new Date()));
        fileindex.setName(filname);
        return fileindexMapper.updateByPrimaryKey(fileindex);
    }

    @Override
    public int deleteFile(int fileid) {
        Fileindex fileindex=fileindexMapper.selectByPrimaryKey(fileid);
        fileindex.setIsdelete(1);
        return fileindexMapper.updateByPrimaryKey(fileindex);
    }

    @Override
    public List<FolderDto> getFolderTree(int userid) {
        List<Folder> list=folderMapper.selectByUserid(userid);
        List<FolderDto> list1=new ArrayList<FolderDto>();
        for(Folder f:list)
        {
            FolderDto folderDto=new FolderDto(f);
            list1.add(folderDto);
        }

        return list1;
    }

    @Override
    public int copyFolder(int folderid, int aimFolderid,int userid) {
        //先把自己给存了
        Folder folderfirst=folderMapper.selectByPrimaryKey(folderid);
        folderfirst.setFatherfolderid(aimFolderid);
        addFolder(folderfirst.getName(),folderfirst.getFatherfolderid(),userid);
       List<Folder> list=new ArrayList<>();
       list.add(folderfirst);
        while (list.size()>0)
        {
            List<Folder> folderList=new ArrayList<>();
            for(Folder folder:list)
            {
                Folder folder1=folderMapper.selectByname(folder.getUserid(),folder.getName(),folder.getFatherfolderid());
                List<Folder> temp=folderMapper.selectByFatherFolder(folder.getFolderid(),userid);//先把旧的取出来
                for(int i=0;i<temp.size();i++)
                {
                    Folder foldertemp=temp.get(i);
                    foldertemp.setFatherfolderid(folder1.getFolderid());
                    temp.set(i,foldertemp);
                    addFolder(foldertemp.getName(),folder1.getFolderid(),userid);//存入数据库
                }
                folderList.addAll(temp);
                //把文件的父目录也改了
                List<Fileindex> fileindexListTemp=fileindexMapper.selectByFatherFolder(folder.getFolderid(),userid);
                for(int i=0;i<fileindexListTemp.size();i++)
                {
                    Fileindex fileindex=fileindexListTemp.get(i);
                    fileindex.setFileid(null);
                    String nowtime=DateUtil.DateToString("yyyy-MM-dd HH:mm:ss", new Date());
                    fileindex.setUploadtime(nowtime);
                    fileindex.setUpdatetime(nowtime);
                    fileindex.setFatherfolder(folder1.getFolderid());
                    fileindexMapper.insert(fileindex);
                }
            }
            list.clear();
            list.addAll(folderList);
        }
        return 0;
    }

    @Override
    public int copyFile(int folderid, int aimFolderid,int userid) {
        Fileindex fileindex=fileindexMapper.selectByPrimaryKey(folderid);
        fileindex.setFileid(null);
        fileindex.setFatherfolder(aimFolderid);
        String nowtime=DateUtil.DateToString("yyyy-MM-dd HH:mm:ss", new Date());
        fileindex.setUploadtime(nowtime);
        fileindex.setUpdatetime(nowtime);
        return fileindexMapper.insert(fileindex);
    }

    @Override
    public boolean isfatherFolder(int folderid, int aimFolderid, int userid) {
        List<Folder> list=folderMapper.selectByFatherFolder(folderid,userid);
        List<Folder> result=new ArrayList<>();
        result.addAll(list);
        while (list.size()>0)
        {
            List<Folder> temp=new ArrayList<>();
            for(Folder folder:list)
            {
                List<Folder> list1=folderMapper.selectByFatherFolder(folder.getFolderid(),userid);
                temp.addAll(list1);
            }
            list.clear();
            list.addAll(temp);
            result.addAll(temp);
        }
        for(Folder folder :result)
        {
            if(folder.getFolderid()==aimFolderid)
                return true;
        }
        return false;
    }

    @Override
    public boolean canmoveorcopy(int folderid, int type, int altertype, int aimFolderid,int userid) {

        if(type==0)
        {
            if(folderid==aimFolderid)
                return false;
            return !isfatherFolder(folderid,aimFolderid,userid);
        }else{
            Fileindex fileindex=fileindexMapper.selectByPrimaryKey(folderid);
            return fileindex.getFatherfolder() != aimFolderid;
        }
    }
    @Override
    public boolean allcanmoveorcopy(List<FileAndFolderDto> list, int userid,int aimFolderid) {
        for(FileAndFolderDto fileAndFolderDto:list)
        {

            if(fileAndFolderDto.getType()==0)
            {
                if(fileAndFolderDto.getId()==aimFolderid)
                    return false;
                if(isfatherFolder(fileAndFolderDto.getId(),aimFolderid,userid))
                    return false;
            }else {
                Fileindex fileindex=fileindexMapper.selectByPrimaryKey(fileAndFolderDto.getId());
                if(fileindex.getFatherfolder() == aimFolderid)
                    return false;
            }
        }
        return true;
    }
    @Override
    public boolean isexitMoveOrCopyNmae(int folderid, int type, int altertype, int aimFolderid, int userid) {
        if(type==0)
        {
            Folder folder=folderMapper.selectByPrimaryKey(folderid);
            return isexist(folder.getName(),userid,aimFolderid);
        }else {
            Fileindex fileindex=fileindexMapper.selectByPrimaryKey(folderid);
            return  isexistfile(fileindex.getName(),userid,aimFolderid);
        }
    }

    @Override
    public boolean isexitMoveOrCopyNmaeinlist(List<FileAndFolderDto> list, int userid, int aimFolderid,int altertype) {
      List<Folder> folderList=folderMapper.selectByFatherFolder(aimFolderid,userid);
      List<Fileindex> fileindexList=fileindexMapper.selectByFatherFolder(aimFolderid,userid);
        for(FileAndFolderDto fileAndFolderDto:list)
        {
            if(fileAndFolderDto.getType()==0)
            {
                for (Folder folder:folderList) {
                        if(folder.getName().equals(fileAndFolderDto.getName()))
                            return true;
                }
            }else {
                for(Fileindex fileindex:fileindexList)
                    if( fileindex.getName().equals(fileAndFolderDto.getName()))
                        return true;
            }
        }
        return false;
    }

    @Override
    public int moverFileAndFolder(int folderid, int type, int aimFolderid) {
        String nowtime=DateUtil.DateToString("yyyy-MM-dd HH:mm:ss", new Date());
        if(type==0)
        {
            Folder folder=folderMapper.selectByPrimaryKey(folderid);
            folder.setFatherfolderid(aimFolderid);
            folder.setUpdatetime(nowtime);
            return folderMapper.updateByPrimaryKey(folder);
        }else {
            Fileindex fileindex=fileindexMapper.selectByPrimaryKey(folderid);
            fileindex.setFatherfolder(aimFolderid);
            fileindex.setUpdatetime(nowtime);
            return fileindexMapper.updateByPrimaryKey(fileindex);
        }
    }


}

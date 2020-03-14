package com.liangrui.hadoop_disk.service.impl;

import com.liangrui.hadoop_disk.bean.dto.FileAndFolderDto;
import com.liangrui.hadoop_disk.bean.entity.*;
import com.liangrui.hadoop_disk.mapper.*;
import com.liangrui.hadoop_disk.service.GroupFileService;
import com.liangrui.hadoop_disk.util.DateUtil;
import com.liangrui.hadoop_disk.util.RowkeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GroupFileServiceImpl implements GroupFileService {
    @Autowired
    private FolderMapper folderMapper;
    @Autowired
    private FileindexMapper fileindexMapper;
    @Autowired
    private DiskuserMapper diskuserMapper;
    @Autowired
    private UploadMapper uploadMapper;

    @Override
    public List<FileAndFolderDto> findAllFileAndFolder(String fatherFolderid) {
        List<Folder> list=folderMapper.selectByFatherFolder(fatherFolderid);
        List<Fileindex> list1=fileindexMapper.selectByFatherFolder(fatherFolderid);
        List<FileAndFolderDto> list2 = new ArrayList<>();
        for(Folder folder:list)
        {
            FileAndFolderDto fileAndFolderDto=new FileAndFolderDto();
            fileAndFolderDto.setId(folder.getFolderid());
            fileAndFolderDto.setName(folder.getName());
            fileAndFolderDto.setSharetype(folder.getSharetype());
            fileAndFolderDto.setUpdatetime(folder.getUpdatetime());
            fileAndFolderDto.setUserid(folder.getUserid());
            fileAndFolderDto.setUsername(diskuserMapper.selectByPrimaryKey(folder.getUserid()).getName());
            list2.add(fileAndFolderDto);

        }
        for(Fileindex fileindex:list1)
        {
            FileAndFolderDto fileAndFolderDto=new FileAndFolderDto();
            fileAndFolderDto.setId(String.valueOf(fileindex.getFileid()));
            fileAndFolderDto.setName(fileindex.getName());
            fileAndFolderDto.setUpdatetime(fileindex.getUpdatetime());
            fileAndFolderDto.setType(1);
            fileAndFolderDto.setSize(fileindex.getSize());
            fileAndFolderDto.setFiletype(fileindex.getFiletype());
            fileAndFolderDto.setSharetype(fileindex.getSharetype());
            fileAndFolderDto.setUsername(diskuserMapper.selectByPrimaryKey(fileindex.getUserid()).getName());
            list2.add(fileAndFolderDto);
        }

        return list2;
    }
    @Override
    public int addFolder(String filename, String fatherFolderid, int userid,int groupid) {
        Folder folder=new Folder();
        RowkeyUtil rowkeyUtil=new RowkeyUtil();
        folder.setFolderid(rowkeyUtil.getRowkey());
        folder.setCreatetime(DateUtil.DateToString("yyyy-MM-dd HH:mm:ss", new Date()));
        folder.setFatherfolderid(fatherFolderid);
        folder.setUpdatetime(DateUtil.DateToString("yyyy-MM-dd HH:mm:ss", new Date()));
        folder.setName(filename);
        folder.setSharetype(0);
        folder.setUserid(userid);
        folder.setIsdelete(0);
        folder.setGroupid(groupid);
        return folderMapper.insert(folder);
    }

    @Override
    public List<FileAndFolderDto> searchbyname(int groupid, String text) {

        List<Folder> list=folderMapper.selectGroupfileByLikeName(groupid,text);
        List<Fileindex> list1=fileindexMapper.selectGroupFileByLikeName(groupid,text);
        List<FileAndFolderDto> list2 = new ArrayList<>();
        for(Folder folder:list)
        {
            FileAndFolderDto fileAndFolderDto=new FileAndFolderDto();
            Diskuser diskuser=diskuserMapper.selectByPrimaryKey(folder.getUserid());
            fileAndFolderDto.setUsername(diskuser.getName());
            fileAndFolderDto.setUserid(folder.getUserid());
            fileAndFolderDto.setId(folder.getFolderid());
            fileAndFolderDto.setName(folder.getName());
            fileAndFolderDto.setSharetype(folder.getSharetype());
            fileAndFolderDto.setUpdatetime(folder.getUpdatetime());
            list2.add(fileAndFolderDto);
        }
        for(Fileindex fileindex:list1)
        {
            FileAndFolderDto fileAndFolderDto=new FileAndFolderDto();
            Diskuser diskuser=diskuserMapper.selectByPrimaryKey(fileindex.getUserid());
            fileAndFolderDto.setUsername(diskuser.getName());
            fileAndFolderDto.setUserid(fileindex.getUserid());
            fileAndFolderDto.setId(String.valueOf(fileindex.getFileid()));
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
    public List<FileAndFolderDto> searchGroupfileByUserid(int groupid, int userid) {
        List<Folder> list=folderMapper.selectGroupfileByuserid(groupid,userid);
        List<Fileindex> list1=fileindexMapper.selectGroupfileByuserid(groupid,userid);
        List<FileAndFolderDto> list2 = new ArrayList<>();
        for(Folder folder:list)
        {
            FileAndFolderDto fileAndFolderDto=new FileAndFolderDto();
            Diskuser diskuser=diskuserMapper.selectByPrimaryKey(folder.getUserid());
            fileAndFolderDto.setUsername(diskuser.getName());
            fileAndFolderDto.setUserid(folder.getUserid());
            fileAndFolderDto.setId(folder.getFolderid());
            fileAndFolderDto.setName(folder.getName());
            fileAndFolderDto.setSharetype(folder.getSharetype());
            fileAndFolderDto.setUpdatetime(folder.getUpdatetime());
            list2.add(fileAndFolderDto);
        }
        for(Fileindex fileindex:list1)
        {
            FileAndFolderDto fileAndFolderDto=new FileAndFolderDto();
            Diskuser diskuser=diskuserMapper.selectByPrimaryKey(fileindex.getUserid());
            fileAndFolderDto.setUsername(diskuser.getName());
            fileAndFolderDto.setUserid(fileindex.getUserid());
            fileAndFolderDto.setId(String.valueOf(fileindex.getFileid()));
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
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public int deleteGroupFile(String id, int type) {
        if(type==0)
        {
            List<Folder> list=folderMapper.selectByFatherFolder(id);
            folderMapper.deleteByPrimaryKey(id);
            List<Fileindex> fileindexList=fileindexMapper.selectByFatherFolder(id);
            //把该文件下的所有文件删除
            for(Fileindex fileindex:fileindexList)
            {
                fileindexMapper.deleteByPrimaryKey(fileindex.getFileid());
            }
            while (list.size()>0) {
                List<Folder> folderList = new ArrayList<>();
                for (Folder folder : list) {
                    List<Folder> folders = folderMapper.selectByFatherFolder(folder.getFolderid());
                    folderList.addAll(folders);
                    List<Fileindex> fileindices = fileindexMapper.selectByFatherFolder(folder.getFolderid());
                    for (Fileindex fileindex : fileindices) {
                        fileindexMapper.deleteByPrimaryKey(fileindex.getFileid());
                    }
                    folderMapper.deleteByPrimaryKey(folder.getFolderid());
                }
                list.clear();
                list.addAll(folderList);
            }
        }else{
            int result= Integer.parseInt(id);
            return fileindexMapper.deleteByPrimaryKey(result);
        }
        return 0;
    }
    @Override
    public boolean isexitNmaeinGrouplist(List<FileAndFolderDto> list, int groupid, String aimFolderid) {
        List<Folder> folderList=folderMapper.selectByFatherFolder(aimFolderid);
        List<Fileindex> fileindexList=fileindexMapper.selectByFatherFolder(aimFolderid);
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
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public int copyFolder(String folderid, String aimFolderid, int groupid) {
        //先把自己给存了
        Folder folderfirst=folderMapper.selectByPrimaryKey(folderid);
        folderfirst.setFatherfolderid(aimFolderid);
        addFolder(folderfirst.getName(),folderfirst.getFatherfolderid(),folderfirst.getUserid(),groupid);
        List<Folder> list=new ArrayList<>();
        list.add(folderfirst);
        while (list.size()>0)
        {
            List<Folder> folderList=new ArrayList<>();
            //插入文件夹
            for(Folder folder:list)
            {
                Folder folder1=folderMapper.selectByname(folder.getName(),folder.getFatherfolderid());
                List<Folder> temp=folderMapper.selectByFatherFolder(folder.getFolderid());//先把旧的取出来
                for(int i=0;i<temp.size();i++)
                {
                    Folder foldertemp=temp.get(i);
                    foldertemp.setFatherfolderid(folder1.getFolderid());
                    temp.set(i,foldertemp);
                    addFolder(foldertemp.getName(),folder1.getFolderid(),folderfirst.getUserid(),groupid);//存入数据库
                }
                folderList.addAll(temp);
                //插入文件
                List<Fileindex> fileindexListTemp=fileindexMapper.selectByFatherFolder(folder.getFolderid());
                for(int i=0;i<fileindexListTemp.size();i++)
                {
                    Fileindex fileindex=fileindexListTemp.get(i);
                    Upload upload=uploadMapper.selectByPrimaryKey(fileindex.getUploadlocationid());
                    upload.setUsernum(upload.getUsernum()+1);
                    fileindex.setFileid(null);
                    String nowtime=DateUtil.DateToString("yyyy-MM-dd HH:mm:ss", new Date());
                    fileindex.setUploadtime(nowtime);
                    fileindex.setUpdatetime(nowtime);
                    fileindex.setGroupid(groupid);
                    fileindex.setFatherfolderid(folder1.getFolderid());
                    fileindexMapper.insert(fileindex);
                }
            }
            list.clear();
            list.addAll(folderList);
        }
        return 0;
    }

    @Override
    public int copyFile(String folderid, String aimFolderid, int groupid) {
        Fileindex fileindex=fileindexMapper.selectByPrimaryKey(Integer.parseInt(folderid));
        Upload upload=uploadMapper.selectByPrimaryKey(fileindex.getUploadlocationid());
        upload.setUsernum(upload.getUsernum()+1);
        fileindex.setFileid(null);
        fileindex.setFatherfolderid(aimFolderid);
        String nowtime=DateUtil.DateToString("yyyy-MM-dd HH:mm:ss", new Date());
        fileindex.setUploadtime(nowtime);
        fileindex.setUpdatetime(nowtime);
        fileindex.setGroupid(groupid);
        return fileindexMapper.insert(fileindex);
    }
}

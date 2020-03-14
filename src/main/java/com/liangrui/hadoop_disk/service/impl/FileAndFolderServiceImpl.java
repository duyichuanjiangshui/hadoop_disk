package com.liangrui.hadoop_disk.service.impl;

import com.liangrui.hadoop_disk.bean.dto.FileAndFolderDto;
import com.liangrui.hadoop_disk.bean.dto.FolderDto;
import com.liangrui.hadoop_disk.bean.entity.Fileindex;
import com.liangrui.hadoop_disk.bean.entity.Folder;
import com.liangrui.hadoop_disk.bean.entity.Upload;
import com.liangrui.hadoop_disk.mapper.FileindexMapper;
import com.liangrui.hadoop_disk.mapper.FolderMapper;
import com.liangrui.hadoop_disk.mapper.UploadMapper;
import com.liangrui.hadoop_disk.service.FileAndFolderService;
import com.liangrui.hadoop_disk.util.DateUtil;
import com.liangrui.hadoop_disk.util.RowkeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileAndFolderServiceImpl implements FileAndFolderService {
    @Autowired
    private FolderMapper folderMapper;
    @Autowired
    private FileindexMapper fileindexMapper;
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
            list2.add(fileAndFolderDto);
        }

        return list2;
    }

    @Override
    public int addFolder(String filename, String fatherFolderid, int userid) {
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
        return folderMapper.insert(folder);
    }

    @Override
    public boolean isexist(String foldername, String fatherFolderid) {
        Folder folder;
        folder=folderMapper.selectByname(foldername,fatherFolderid);
        return folder!=null;
    }

    @Override
    public int delteFolder(String folderid) {
       //删除其实是伪善除
        Folder folder=folderMapper.selectByPrimaryKey(folderid);
        folder.setIsdelete(1);
        folder.setDeletetime(DateUtil.DateToString("yyyy-MM-dd HH:mm:ss", new Date()));

        return folderMapper.updateByPrimaryKey(folder);
    }

    @Override
    public int updateFoler(String foldername, String folderid) {
        Folder folder=folderMapper.selectByPrimaryKey(folderid);
        folder.setUpdatetime(DateUtil.DateToString("yyyy-MM-dd HH:mm:ss", new Date()));
        folder.setName(foldername);
        return folderMapper.updateByPrimaryKey(folder);
    }

    @Override
    public int changesharetype(int sharetype, int type, String fileid) {
        if(type==0)
        {
            Folder folder=folderMapper.selectByPrimaryKey(fileid);
            folder.setSharetype(sharetype);
            return folderMapper.updateByPrimaryKey(folder);
        }else{
            Fileindex fileindex=fileindexMapper.selectByPrimaryKey(Integer.parseInt(fileid));
            fileindex.setSharetype(sharetype);
            return fileindexMapper.updateByPrimaryKey(fileindex);
        }
    }

    @Override
    public boolean isexistfile(String filename, String fatherFolderid) {
        Fileindex fileindex;
        fileindex=fileindexMapper.selectByname(filename,fatherFolderid);
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
        fileindex.setDeletetime(DateUtil.DateToString("yyyy-MM-dd HH:mm:ss", new Date()));
        return fileindexMapper.updateByPrimaryKey(fileindex);
    }

    @Override
    public List<FolderDto> getFolderTree(int userid) {
        List<Folder> list=folderMapper.selectByUserid(userid);
        List<FolderDto> list1=new ArrayList<>();
        for(Folder f:list)
        {
            FolderDto folderDto=new FolderDto(f);
            list1.add(folderDto);
        }

        return list1;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public int copyFolder(String folderid, String aimFolderid, int userid) {
        //先把自己给存了
        Folder folderfirst=folderMapper.selectByPrimaryKey(folderid);
        folderfirst.setFatherfolderid(aimFolderid);
        addFolder(folderfirst.getName(),folderfirst.getFatherfolderid(),userid);
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
                    addFolder(foldertemp.getName(),folder1.getFolderid(),userid);//存入数据库
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
    public int copyFile(String folderid, String aimFolderid, int userid) {
        Fileindex fileindex=fileindexMapper.selectByPrimaryKey(Integer.parseInt(folderid));
        Upload upload=uploadMapper.selectByPrimaryKey(fileindex.getUploadlocationid());
        upload.setUsernum(upload.getUsernum()+1);
        fileindex.setFileid(null);
        fileindex.setFatherfolderid(aimFolderid);
        String nowtime=DateUtil.DateToString("yyyy-MM-dd HH:mm:ss", new Date());
        fileindex.setUploadtime(nowtime);
        fileindex.setUpdatetime(nowtime);
        return fileindexMapper.insert(fileindex);
    }

    @Override
    public boolean isfatherFolder(String folderid, String aimFolderid, int userid) {
        List<Folder> list=folderMapper.selectByFatherFolder(folderid);
        List<Folder> result=new ArrayList<>();
        result.addAll(list);
        while (list.size()>0)
        {
            List<Folder> temp=new ArrayList<>();
            for(Folder folder:list)
            {
                List<Folder> list1=folderMapper.selectByFatherFolder(folder.getFolderid());
                temp.addAll(list1);
            }
            list.clear();
            list.addAll(temp);
            result.addAll(temp);
        }
        for(Folder folder :result)
        {
            if(folder.getFolderid().equals(aimFolderid))
                return true;
        }
        return false;
    }

    @Override
    public boolean canmoveorcopy(String folderid, int type, int altertype, String aimFolderid, int userid) {

        if(type==0)
        {
            if(folderid.equals(aimFolderid))
                return false;
            return !isfatherFolder(folderid,aimFolderid,userid);
        }else{
            Fileindex fileindex=fileindexMapper.selectByPrimaryKey(Integer.parseInt(folderid));
            return !fileindex.getFatherfolderid().equals(aimFolderid) ;
        }
    }
    @Override
    public boolean allcanmoveorcopy(List<FileAndFolderDto> list, int userid,String aimFolderid) {
        for(FileAndFolderDto fileAndFolderDto:list)
        {

            if(fileAndFolderDto.getType()==0)
            {
                if(fileAndFolderDto.getId().equals(aimFolderid))
                    return false;
                if(isfatherFolder(fileAndFolderDto.getId(),aimFolderid,userid))
                    return false;
            }else {
                Fileindex fileindex=fileindexMapper.selectByPrimaryKey(Integer.parseInt(fileAndFolderDto.getId()));
                if(fileindex.getFatherfolderid().equals(aimFolderid))
                    return false;
            }
        }
        return true;
    }
    @Override
    public boolean isexitMoveOrCopyNmae(String folderid, int type, String aimFolderid, int userid) {
        if(type==0)
        {
            Folder folder=folderMapper.selectByPrimaryKey(folderid);
            return isexist(folder.getName(),aimFolderid);
        }else {
            Fileindex fileindex=fileindexMapper.selectByPrimaryKey(Integer.parseInt(folderid));
            return  isexistfile(fileindex.getName(),aimFolderid);
        }
    }

    @Override
    public boolean isexitMoveOrCopyNmaeinlist(List<FileAndFolderDto> list, int userid, String aimFolderid) {
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
    public int moverFileAndFolder(String folderid, int type, String aimFolderid) {
        String nowtime=DateUtil.DateToString("yyyy-MM-dd HH:mm:ss", new Date());//把现在的时间转换成字符串
        if(type==0)
        {
            Folder folder=folderMapper.selectByPrimaryKey(folderid);
            folder.setFatherfolderid(aimFolderid);
            folder.setUpdatetime(nowtime);
            return folderMapper.updateByPrimaryKey(folder);
        }else {
            Fileindex fileindex=fileindexMapper.selectByPrimaryKey(Integer.parseInt(folderid));

            fileindex.setFatherfolderid(aimFolderid);
            fileindex.setUpdatetime(nowtime);
            return fileindexMapper.updateByPrimaryKey(fileindex);
        }
    }
    public List<FileAndFolderDto> findByLikeName(int userid,String name,String rootFolderid){
        String fatherFoldername;//父文件名
        String fatherfolderid;//父id
        List<Folder> list=folderMapper.selectByLikeName(userid,name);
        List<Fileindex> list1=fileindexMapper.selectByLikeName(userid,name);
        List<FileAndFolderDto> list2 = new ArrayList<>();
        for(Folder folder:list)
        {
            FileAndFolderDto fileAndFolderDto=new FileAndFolderDto();
            if(folder.getFatherfolderid().equals(rootFolderid))
            {
                fatherFoldername="全部文件";
                fatherfolderid=rootFolderid;
            }else{
                Folder folder1=folderMapper.selectByPrimaryKey(folder.getFatherfolderid());
                fatherFoldername=folder1.getName();
                fatherfolderid=folder1.getFatherfolderid();
            }
            fileAndFolderDto.setId(folder.getFolderid());
            fileAndFolderDto.setName(folder.getName());
            fileAndFolderDto.setSharetype(folder.getSharetype());
            fileAndFolderDto.setUpdatetime(folder.getUpdatetime());
            fileAndFolderDto.setFatherfoldername(fatherFoldername);
            fileAndFolderDto.setFatherfolderid(fatherfolderid);
            list2.add(fileAndFolderDto);
        }
        for(Fileindex fileindex:list1)
        {
            if(fileindex.getFatherfolderid().equals(rootFolderid))
            {
                fatherFoldername="全部文件";
                fatherfolderid=rootFolderid;
            }else{
                Folder folder=folderMapper.selectByPrimaryKey(fileindex.getFatherfolderid());
                fatherFoldername=folder.getName();
                fatherfolderid=fileindex.getFatherfolderid();
            }
            FileAndFolderDto fileAndFolderDto=new FileAndFolderDto();
            fileAndFolderDto.setFatherfoldername(fatherFoldername);
            fileAndFolderDto.setId(String.valueOf(fileindex.getFileid()));
            fileAndFolderDto.setName(fileindex.getName());
            fileAndFolderDto.setUpdatetime(fileindex.getUpdatetime());
            fileAndFolderDto.setType(1);
            fileAndFolderDto.setSize(fileindex.getSize());
            fileAndFolderDto.setFiletype(fileindex.getFiletype());
            fileAndFolderDto.setSharetype(fileindex.getSharetype());
            fileAndFolderDto.setFatherfolderid(fatherfolderid);
            list2.add(fileAndFolderDto);
        }

        return list2;
    }

    @Override
    public List<FileAndFolderDto> findByFiltype(int userid, int type,String rootFolderid) {
        //文档 图片  音乐 视频
        String fatherFoldername;//父文件名
        String fatherfolderid;//父id

        List<Fileindex> list1=fileindexMapper.selectbyFiletype(userid,type);
        List<FileAndFolderDto> list2 = new ArrayList<>();

        for(Fileindex fileindex:list1)
        {
            if(fileindex.getFatherfolderid().equals(rootFolderid))
            {
                fatherFoldername="全部文件";
                fatherfolderid=rootFolderid;
            }else{
                Folder folder=folderMapper.selectByPrimaryKey(fileindex.getFatherfolderid());
                fatherFoldername=folder.getName();
                fatherfolderid=fileindex.getFatherfolderid();
            }
            FileAndFolderDto fileAndFolderDto=new FileAndFolderDto();
            fileAndFolderDto.setFatherfoldername(fatherFoldername);
            fileAndFolderDto.setId(String.valueOf(fileindex.getFileid()));
            fileAndFolderDto.setName(fileindex.getName());
            fileAndFolderDto.setUpdatetime(fileindex.getUpdatetime());
            fileAndFolderDto.setType(1);
            fileAndFolderDto.setSize(fileindex.getSize());
            fileAndFolderDto.setFiletype(fileindex.getFiletype());
            fileAndFolderDto.setSharetype(fileindex.getSharetype());
            fileAndFolderDto.setFatherfolderid(fatherfolderid);
            list2.add(fileAndFolderDto);
        }
        return list2;
    }

    @Override
    public List<FileAndFolderDto> findalonefileByFiltype(int userid, int type,String name,String rootname) {
        //文档 图片  音乐 视频
        String fatherFoldername;//父文件名
        String fatherfolderid;//父id

        List<Fileindex> list1=fileindexMapper.selectAlonefileByLikeName(userid,type,name);
        List<FileAndFolderDto> list2 = new ArrayList<>();

        for(Fileindex fileindex:list1)
        {
            if(fileindex.getFatherfolderid().equals(rootname))
            {
                fatherFoldername="全部文件";
                fatherfolderid=rootname;
            }else{
                Folder folder=folderMapper.selectByPrimaryKey(fileindex.getFatherfolderid());
                fatherFoldername=folder.getName();
                fatherfolderid=fileindex.getFatherfolderid();
            }
            FileAndFolderDto fileAndFolderDto=new FileAndFolderDto();
            fileAndFolderDto.setFatherfoldername(fatherFoldername);
            fileAndFolderDto.setId(String.valueOf(fileindex.getFileid()));
            fileAndFolderDto.setName(fileindex.getName());
            fileAndFolderDto.setUpdatetime(fileindex.getUpdatetime());
            fileAndFolderDto.setType(1);
            fileAndFolderDto.setSize(fileindex.getSize());
            fileAndFolderDto.setFiletype(fileindex.getFiletype());
            fileAndFolderDto.setSharetype(fileindex.getSharetype());
            fileAndFolderDto.setFatherfolderid(fatherfolderid);
            list2.add(fileAndFolderDto);
        }
        return list2;
    }

    @Override
    public List<FileAndFolderDto> findrecyclerfile(int userid,String rootfolderid) {
        String fatherFoldername;//父文件名
        String fatherfolderid;//父id
        List<Folder> list=folderMapper.selectrecycle(userid);
        List<Fileindex> list1=fileindexMapper.selectrecycle(userid);
        List<FileAndFolderDto> list2 = new ArrayList<>();
        for(Folder folder:list)
        {
            FileAndFolderDto fileAndFolderDto=new FileAndFolderDto();
            if(folder.getFatherfolderid().equals(rootfolderid))
            {
                fatherFoldername="全部文件";
                fatherfolderid=rootfolderid;
            }else{
                Folder folder1=folderMapper.selectByPrimaryKey(folder.getFatherfolderid());
                fatherFoldername=folder1.getName();
                fatherfolderid=folder.getFatherfolderid();
            }
            fileAndFolderDto.setId(folder.getFolderid());
            fileAndFolderDto.setName(folder.getName());
            fileAndFolderDto.setDeltetime(folder.getDeletetime());
            fileAndFolderDto.setFatherfoldername(fatherFoldername);
            fileAndFolderDto.setFatherfolderid(fatherfolderid);
            list2.add(fileAndFolderDto);
        }
        for(Fileindex fileindex:list1)
        {
            if(fileindex.getFatherfolderid().equals(rootfolderid))
            {
                fatherFoldername="全部文件";
                fatherfolderid=rootfolderid;
            }else{
                Folder folder=folderMapper.selectByPrimaryKey(fileindex.getFatherfolderid());
                fatherFoldername=folder.getName();
                fatherfolderid=fileindex.getFatherfolderid();
            }
            FileAndFolderDto fileAndFolderDto=new FileAndFolderDto();
            fileAndFolderDto.setFatherfoldername(fatherFoldername);
            fileAndFolderDto.setId(String.valueOf(fileindex.getFileid()));
            fileAndFolderDto.setName(fileindex.getName());
            fileAndFolderDto.setUpdatetime(fileindex.getUpdatetime());
            fileAndFolderDto.setType(1);
            fileAndFolderDto.setSize(fileindex.getSize());
            fileAndFolderDto.setFiletype(fileindex.getFiletype());
            fileAndFolderDto.setSharetype(fileindex.getSharetype());
            fileAndFolderDto.setFatherfolderid(fatherfolderid);
            fileAndFolderDto.setDeltetime(fileindex.getDeletetime());
            list2.add(fileAndFolderDto);
        }
        return list2;
    }
//回收站
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public int delterecycle(int userid, String fileOrFolderid, int type) {
        if(type==1)
        {
            Fileindex fileindex=fileindexMapper.selectByPrimaryKey(Integer.parseInt(fileOrFolderid));
            Upload upload=uploadMapper.selectByPrimaryKey(fileindex.getUploadlocationid());
            upload.setUsernum(upload.getUsernum()-1);
            uploadMapper.updateByPrimaryKey(upload); //这个资源的用户减一
            return fileindexMapper.deleteByPrimaryKey(Integer.valueOf(fileOrFolderid));
        }else{
            folderMapper.deleteByPrimaryKey(fileOrFolderid);
            List<FileAndFolderDto> list=findAllFileAndFolder(fileOrFolderid);
            while(list.size()>0)
            {
                List<FileAndFolderDto> list1=new ArrayList<>();
                for(FileAndFolderDto fileAndFolderDto:list)
                {
                   if(fileAndFolderDto.getType()==1)//是文件就直接删除
                   {
                       Fileindex fileindex=fileindexMapper.selectByPrimaryKey(Integer.parseInt(fileOrFolderid));
                       Upload upload=uploadMapper.selectByPrimaryKey(fileindex.getUploadlocationid());
                       upload.setUsernum(upload.getUsernum()-1);
                       uploadMapper.updateByPrimaryKey(upload);
                       fileindexMapper.deleteByPrimaryKey(Integer.valueOf(fileAndFolderDto.getId()));
                   }else{
                       List<FileAndFolderDto> list2=findAllFileAndFolder(fileOrFolderid);
                       list1.addAll(list2);
                       folderMapper.deleteByPrimaryKey(fileAndFolderDto.getId());
                   }
                }
                list.clear();
                list.addAll(list1);
            }
        }
        return 0;
    }

    @Override
    public int recycle(int userid, String folderid, int type) {
        if (type == 0) {
            Folder folder = folderMapper.selectByPrimaryKey(folderid);
            folder.setIsdelete(0);
            return folderMapper.updateByPrimaryKey(folder);
        } else
            {Fileindex fileindex=fileindexMapper.selectByPrimaryKey(Integer.parseInt(folderid));
            fileindex.setIsdelete(0);
            return fileindexMapper.updateByPrimaryKey(fileindex);
        }
    }
}

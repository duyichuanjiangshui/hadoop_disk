package com.liangrui.hadoop_disk.service.impl;

import com.liangrui.hadoop_disk.bean.dto.FileAndFolderDto;
import com.liangrui.hadoop_disk.bean.dto.FolderDto;
import com.liangrui.hadoop_disk.bean.entity.*;
import com.liangrui.hadoop_disk.bean.model.MyShareModel;
import com.liangrui.hadoop_disk.mapper.*;
import com.liangrui.hadoop_disk.service.GroupFileService;
import com.liangrui.hadoop_disk.service.ShareService;
import com.liangrui.hadoop_disk.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class ShareServiceImpl implements ShareService {
    @Autowired
    private SharefileMapper sharefileMapper;
    @Autowired
    private SharedetailMapper sharedetailMapper;
    @Autowired
    private ResgroupMapper resgroupMapper;
    @Autowired
    private FolderMapper folderMapper;
    @Autowired
    private GroupFileService groupFileService;
    @Autowired
    private FileindexMapper fileindexMapper;

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void addShare(List<FileAndFolderDto> list, Sharefile sharefile) {

        sharefile.setStarttime(DateUtil.DateToString("yyyy-MM-dd HH:mm:ss", new Date()));
        sharefileMapper.insert(sharefile);
        Sharefile sharefile1=sharefileMapper.fildFileByshareUrl(sharefile.getShareurl());
        for(FileAndFolderDto fileAndFolderDto:list)
        {
            Sharedetail sharedetail=new Sharedetail();
            sharedetail.setResourcetype(fileAndFolderDto.getType());
            sharedetail.setShareid(sharefile1.getShareid());
            sharedetail.setResourcetypeid(fileAndFolderDto.getId());
            sharedetailMapper.insert(sharedetail);
        }
    }

    @Override
    public Resgroup findbygroupid(int groupid) {
        return resgroupMapper.selectByPrimaryKey(groupid);
    }

    @Override
    public List<FolderDto> getFolderTree(int groupid) {
        List<Folder> list=folderMapper.selectByGroupid(groupid);
        List<FolderDto> list1=new ArrayList<>();
        for(Folder f:list)
        {
            FolderDto folderDto=new FolderDto(f);
            list1.add(folderDto);
        }
        return list1;
    }



    @Override
    public void shareinGroup(List<FileAndFolderDto> list, int groupid, String aimFolderid) {
        for(FileAndFolderDto fileAndFolderDto:list)
        {
            if (fileAndFolderDto.getType() == 0) {
                groupFileService.copyFolder(fileAndFolderDto.getId(), aimFolderid, groupid);
            } else {
                groupFileService.copyFile(fileAndFolderDto.getId(), aimFolderid, groupid);
            }
        }
    }

    @Override
    public Sharefile getfilebyShareurl(String shareurl) {
        return sharefileMapper.fildFileByshareUrl(shareurl);
    }

    @Override
    public int sharefileIsLowNewdate(String datestring,String day) {
        try {
            Date date1=DateUtil.StringToDate("yyyy-MM-dd HH:mm:ss",datestring);
            if(day!=null)
            {
                date1=DateUtil.addDate(date1, Long.parseLong(day));
                Date date2=new Date();
                return date1.compareTo(date2);
            }else{
                return 1;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<FileAndFolderDto> getsharedetail(String shareUrl) {
        List<FileAndFolderDto> list2 = new ArrayList<>();
        Sharefile sharefile=sharefileMapper.fildFileByshareUrl(shareUrl);
        if (sharefile.getStatue() == null) {
            sharefile.setStatue(0);
        } else {
            sharefile.setStatue(sharefile.getStatue() + 1);
        }
        sharefileMapper.updateByPrimaryKey(sharefile);//更新浏览其实
        List<Sharedetail> list=sharedetailMapper.findAllSharedetailByShareid(sharefile.getShareid());
        for(Sharedetail sharedetail:list)
        {
            if(sharedetail.getResourcetype()==0)
            {
                Folder folder=folderMapper.selectByPrimaryKey(sharedetail.getResourcetypeid());
                FileAndFolderDto fileAndFolderDto=new FileAndFolderDto();
                fileAndFolderDto.setId(folder.getFolderid());
                fileAndFolderDto.setName(folder.getName());
                fileAndFolderDto.setSharetype(folder.getSharetype());
                fileAndFolderDto.setUpdatetime(folder.getUpdatetime());
                list2.add(fileAndFolderDto);
            }
            else{
                Fileindex fileindex=fileindexMapper.selectByPrimaryKey(Integer.valueOf(sharedetail.getResourcetypeid()));
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
        }
        return list2;
    }

    @Override
    public List<MyShareModel> getMyShare(int userid) {
        List<Sharefile> list=sharefileMapper.fildFileByUserid(userid);
        List<MyShareModel> list1=new ArrayList<>();
        for(Sharefile sharefile:list)
        {
            MyShareModel myShareModel=new MyShareModel();
            myShareModel.setPassword(sharefile.getPassword());
            if (sharefile.getSharetime() != null) {
                if(sharefileIsLowNewdate(sharefile.getStarttime(), sharefile.getSharetime())!=1)
                myShareModel.setSharetime("已失效");
                    else
                {
                    try {
                        Date date1=DateUtil.StringToDate("yyyy-MM-dd HH:mm:ss",sharefile.getStarttime());
                        date1=DateUtil.addDate(date1, Long.parseLong(sharefile.getSharetime()));
                        myShareModel.setSharetime(DateUtil.DateToString("yyyy-MM-dd HH:mm:ss",date1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                myShareModel.setSharetime("永久有效");
            }
            myShareModel.setShareurl("http://localhost:8000/hadoop/share/s?shareUrl="+sharefile.getShareurl());
            if (sharefile.getStatue() == null) {
                myShareModel.setViewshareNumber(0);
            } else {
                myShareModel.setViewshareNumber(sharefile.getStatue());
            }
            myShareModel.setStarttime(sharefile.getStarttime());
            myShareModel.setId(sharefile.getShareid());
            list1.add(myShareModel);
        }
        return list1;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public int delete(int id) {
        sharedetailMapper.deleteBy(id);
        sharefileMapper.deleteByPrimaryKey(id);

        return 0;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void deleteall(List<MyShareModel> list) {
        for(MyShareModel myShareModel:list)
        {
            sharedetailMapper.deleteBy(myShareModel.getId());
            sharefileMapper.deleteByPrimaryKey(myShareModel.getId());
        }

    }
}

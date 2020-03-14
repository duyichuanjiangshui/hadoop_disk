package com.liangrui.hadoop_disk.service.impl;

import com.liangrui.hadoop_disk.bean.dto.FileAndFolderDto;
import com.liangrui.hadoop_disk.bean.dto.FolderDto;
import com.liangrui.hadoop_disk.bean.entity.*;
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
}

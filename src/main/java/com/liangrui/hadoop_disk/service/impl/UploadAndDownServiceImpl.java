package com.liangrui.hadoop_disk.service.impl;

import com.liangrui.hadoop_disk.bean.entity.Fileindex;
import com.liangrui.hadoop_disk.bean.entity.Upload;
import com.liangrui.hadoop_disk.mapper.FileindexMapper;
import com.liangrui.hadoop_disk.mapper.UploadMapper;
import com.liangrui.hadoop_disk.service.UploadAndDownService;
import com.liangrui.hadoop_disk.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

@Service
public class UploadAndDownServiceImpl implements UploadAndDownService {
    @Autowired
    UploadMapper uploadMapper;
    @Autowired
    FileindexMapper fileindexMapper;
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public int uploadFile(MultipartFile file,int userid,String fatherFolderid) {
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);//获取文件类型
        int sorttype=SortFileUtil.sortFile(suffix);
        //小于10MB的存入hbase
        String nowtime=DateUtil.DateToString("yyyy-MM-dd HH:mm:ss", new Date());
        Upload upload=new Upload();
        upload.setUserid(userid);
        upload.setUploadtime(nowtime);
        upload.setOrignalname(file.getOriginalFilename());
        upload.setUsernum(1);
        Fileindex fileindex=new Fileindex();
        fileindex.setUserid(userid);
        fileindex.setIsdelete(0);
        fileindex.setSharetype(0);
        fileindex.setName(file.getOriginalFilename());
        fileindex.setSavenum(1);
        fileindex.setSize((float) file.getSize());
        fileindex.setUploadtime(nowtime);
        fileindex.setUpdatetime(nowtime);
        fileindex.setFatherfolderid(fatherFolderid);
        fileindex.setFiletype(suffix);
        fileindex.setSortype(sorttype);
        if(file.getSize()<10240000)
        {
            try {
                RowkeyUtil rowkeyUtil=new RowkeyUtil();
                String rowKey=rowkeyUtil.getRowkey();
                //rowkey作为唯一标识
                //再将file转换成String
                String bytestream= FileChange.encodeBase64File(file);
                //将二进制流存入hbase habase的type为0
                HbaseUtil.insertHbaseData(rowKey,bytestream);
                upload.setUploadlocation(rowKey);
                upload.setUploadtype(0);
                int flag1=uploadMapper.insert(upload);
                int uploadLocationid=uploadMapper.selectuploadLocation(rowKey);
                fileindex.setUploadlocationid(uploadLocationid);
                int flag2=fileindexMapper.insert(fileindex);
                return flag1*flag2;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            DateUtil dateUtil=new DateUtil();
            Calendar calendar=Calendar.getInstance();
            String aimDir="/"+dateUtil.getYear(calendar)+"/"+dateUtil.getMonth(calendar)
                    +"/"+dateUtil.getDay(calendar);

            try {

                upload.setUploadtype(1);//代表上传hdfs
                String uploadLoation= HDFSUtil.createFile(file,aimDir);
                upload.setUploadlocation(uploadLoation);
                int flag1=uploadMapper.insert(upload);
                int uploadLocationid=uploadMapper.selectuploadLocation(uploadLoation);
                fileindex.setUploadlocationid(uploadLocationid);
                int flag2=fileindexMapper.insert(fileindex);
                return flag1*flag2;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
    //filid 文件的id
    public void downFile(HttpServletResponse response, String fileindexid)
    {
        Fileindex fileindex=fileindexMapper.selectByPrimaryKey(Integer.valueOf(fileindexid));
        Upload upload=uploadMapper.selectByPrimaryKey(fileindex.getUploadlocationid());

        String data;
        String catalogue="D:\\downtemp\\";
        //为了避免重复
        RowkeyUtil rowkeyUtil=new RowkeyUtil();
        String filepath=catalogue+ rowkeyUtil.getRowkey()+fileindex.getName();
        if(upload.getUploadtype()==0)
        {
            try {
                data=HbaseUtil.getData(upload.getUploadlocation());
                FileChange.decoderBase64File(data,filepath, catalogue );
                DownloadUtil.fileDownload(response,filepath,fileindex.getName());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }else {
            try {
                HDFSUtil.downFile(upload.getUploadlocation(),filepath);
                DownloadUtil.fileDownload(response,filepath,fileindex.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

}

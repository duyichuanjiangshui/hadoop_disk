package com.liangrui.hadoop_disk.controller;

import com.liangrui.hadoop_disk.bean.model.ProgressModel;
import com.liangrui.hadoop_disk.util.*;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/uploadAndDown")
public class UploadAndController {
    @PostMapping("/upload")
    @ResponseBody
    public Map<String, Object> upload(MultipartFile file){
        Map<String, Object> result = new HashMap<>();
        if (file != null && !file.isEmpty()){
            RowkeyUtil rowkeyUtil=new RowkeyUtil();
            String rowKey=rowkeyUtil.getRowkey();
            System.out.println(rowKey);
            System.out.println(file.getOriginalFilename());
            //rowkey作为唯一标识
            //再将file转换成String
            //小于10MB的存入hbase
            if(file.getSize()<10240)
            {
                try {
                    String bytestream= FileChange.encodeBase64File(file);
                    //将二进制流存入hbase
                    HbaseUtil.insertHbaseData(rowKey,bytestream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                DateUtil dateUtil=new DateUtil();
                Calendar calendar=Calendar.getInstance();
                String aimDir="/"+dateUtil.getYear(calendar)+"/"+dateUtil.getMonth(calendar)
                        +"/"+dateUtil.getDay(calendar);
                try {
                    HDFSUtil.createFile(file,aimDir);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //上传到服务器file.transferTo(new File("d:/"+file.getOriginalFilename()));
                result.put("code", 0);
                result.put("msg", "success");

        } else {
            result.put("code", -1);
            result.put("msg", "未获取到有效的文件信息，请重新上传!");
        }
        return result;
    }
    /**
     * 获取文件上传进度
     * @param request
     * @return
     */
    @RequestMapping("getUploadProgress")
    @ResponseBody
    public ProgressModel getUploadProgress(HttpServletRequest request){
        return (ProgressModel) request.getSession().getAttribute("uploadStatus");
    }
    @RequestMapping("/uploadIndex")
    public String test()
    {
        return "upload";
    }
}

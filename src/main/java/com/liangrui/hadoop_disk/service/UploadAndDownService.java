package com.liangrui.hadoop_disk.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface UploadAndDownService {
    int uploadFile(MultipartFile file,int userid,String fatherFolderid);
    public void downFile(HttpServletResponse response, String fileindexid);
}

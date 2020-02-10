package com.liangrui.hadoop_disk.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadAndDownService {
    int uploadFile(MultipartFile file,int userid,int fatherFolderid);
}

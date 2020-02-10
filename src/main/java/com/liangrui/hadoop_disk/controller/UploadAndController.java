package com.liangrui.hadoop_disk.controller;

import com.liangrui.hadoop_disk.bean.model.ProgressModel;
import com.liangrui.hadoop_disk.service.UploadAndDownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/uploadAndDown")
public class UploadAndController {
    @Autowired
   private UploadAndDownService uploadAndDownService;
    @PostMapping("/upload")
    @ResponseBody
    public Map<String, Object> upload(MultipartFile file,int fatherFolderid){
        int userid=1;//session
        Map<String, Object> result = new HashMap<>();
        if (file != null && !file.isEmpty()){
               int flag=uploadAndDownService.uploadFile(file,userid,fatherFolderid);
            //上传到服务器file.transferTo(new File("d:/"+file.getOriginalFilename()));
            if(flag!=0)
            {
                result.put("code", 0);
                result.put("msg", "success");
            }else {
                result.put("code", 1);
                result.put("msg", "上传失败");
            }
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
    public String test(Model model, int fatherFolderid)
    {
        model.addAttribute("fatherFolderid",fatherFolderid);
        return "upload";
    }
}

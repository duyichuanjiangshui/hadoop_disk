package com.liangrui.hadoop_disk.controller;

import com.liangrui.hadoop_disk.bean.model.ProgressModel;
import com.liangrui.hadoop_disk.service.UploadAndDownService;
import com.liangrui.hadoop_disk.util.RowkeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/uploadAndDown")
public class UploadAndController {
    @Autowired
   private UploadAndDownService uploadAndDownService;
    @PostMapping("/upload")
    @ResponseBody
    public Map<String, Object> upload(MultipartFile file,String fatherFolderid){
        int userid=1;//session
        Map<String, Object> result = new HashMap<>();
        if (file != null && !file.isEmpty()){
               int flag=uploadAndDownService.uploadFile(file,userid,fatherFolderid);
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
    @RequestMapping("/down")
    public void  down(HttpServletResponse response, String fileindexid)
    {
       uploadAndDownService.downFile(response,fileindexid);
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
    public String test(Model model, String fatherFolderid)
    {
        model.addAttribute("fatherFolderid",fatherFolderid);
        return "upload";
    }

    //图片上传测试
    @RequestMapping("/uploadImg")
    @ResponseBody
    public Map<String, Object> uploadImg(MultipartFile file,HttpServletRequest request){

        String prefix="";
        String dateStr="";
        //保存上传
        OutputStream out = null;
        InputStream fileInput=null;
        try{
            if(file!=null){
                String originalName = file.getOriginalFilename();
                prefix=originalName.substring(originalName.lastIndexOf(".")+1);
                Date date = new Date();
                RowkeyUtil rowkeyUtil=new RowkeyUtil();
                String uuid = rowkeyUtil.getRowkey();
                String filepath = "C:\\Users\\12493\\Desktop\\java\\hadoop_disk\\src\\main\\resources\\static\\images\\"+uuid+"." + prefix;


                File files=new File(filepath);
                //打印查看上传路径
                System.out.println(filepath);
                if(!files.getParentFile().exists()){
                    files.getParentFile().mkdirs();
                }
                file.transferTo(files);
                Map<String,Object> map2=new HashMap<>();
                Map<String,Object> map=new HashMap<>();
                map.put("code",0);
                map.put("msg","");
                map.put("data",map2);
                map2.put("src","/hadoop/static/images/"+uuid+"." + prefix);
                return map;
            }
        }catch (Exception e){
        }finally{
            try {
                if(out!=null){
                    out.close();
                }
                if(fileInput!=null){
                    fileInput.close();
                }
            } catch (IOException e) {
            }
        }
        Map<String,Object> map=new HashMap<>();
        map.put("code",1);
        map.put("msg","");
        return map;

    }

}

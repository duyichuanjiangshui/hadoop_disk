package com.liangrui.hadoop_disk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liangrui.hadoop_disk.bean.dto.FileAndFolderDto;
import com.liangrui.hadoop_disk.bean.dto.GroupDto;
import com.liangrui.hadoop_disk.bean.entity.Sharedetail;
import com.liangrui.hadoop_disk.bean.entity.Sharefile;
import com.liangrui.hadoop_disk.bean.model.PageModel;
import com.liangrui.hadoop_disk.service.FileAndFolderService;
import com.liangrui.hadoop_disk.service.FriendAndGroupService;
import com.liangrui.hadoop_disk.service.GroupFileService;
import com.liangrui.hadoop_disk.util.RowkeyUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/groupfile")
public class GroupFileController {
    @Autowired
    FriendAndGroupService friendAndGroupService;
    @Autowired
    GroupFileService groupFileService;
    @Autowired
    FileAndFolderService fileAndFolderService;
    @RequestMapping("index")
    public String getGroupFilePage(Model model, int groupid, HttpServletRequest httpServletRequest)
    {
        int userid= (int) httpServletRequest.getSession().getAttribute("userid");
       GroupDto groupDto=friendAndGroupService.findGroupdao(groupid,userid);
        System.out.println(JSON.toJSONString(groupDto));
       model.addAttribute("fatherFolderid", groupDto.getRootfolder());
       model.addAttribute("isowner",groupDto.getIsowner());
       model.addAttribute("groupid",groupid);
        System.out.println(JSON.toJSONString(groupDto));
       return "groupfile";
    }
    @RequestMapping("/allFolder")
    @ResponseBody
    public Map<String, Object> allFolder(PageModel<List<FileAndFolderDto>> page, Model model, String fatherFolder) {
        List<FileAndFolderDto> list = groupFileService.findAllFileAndFolder(fatherFolder);
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "查询完成");
        data.put("count", page.getCount());
        data.put("data", list);
        model.addAttribute("fatherid", fatherFolder);
        return data;
    }
    //添加文件夹
    @RequestMapping("/addFolder")
    @ResponseBody
    public Map<String, Object> addFolder(HttpServletRequest httpServletRequest,@Param("filename") String filename, @Param("fatherFolderid") String fatherFolderid,@Param("gropid") int groupid) {
        int userid= (int) httpServletRequest.getSession().getAttribute("userid");
        Map<String, Object> rs = new HashMap<>();
        if (fileAndFolderService.isexist(filename, fatherFolderid)) {
            rs.put("code", 1);
            rs.put("msg", "文件夹名不可重复");
            return rs;
        }
        int result = groupFileService.addFolder(filename, fatherFolderid,userid,groupid);
        if (result == 0) {
            rs.put("code", 1);
            rs.put("msg", "添加失败");
        } else {
            rs.put("code", 0);
            rs.put("msg", "添加成功");
        }
        return rs;
    }
    @RequestMapping("/searchbylikename")
    @ResponseBody
    public Map<String, Object> searchbylikename(int groupid, String text) {
        List<FileAndFolderDto> list = groupFileService.searchbyname(groupid,text);
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "查询完成");
        data.put("data", list);
        return data;
    }
    @RequestMapping("/selectbyuserid")
    @ResponseBody
    public Map<String, Object> searchbyuserid(int groupid, int userid) {
        List<FileAndFolderDto> list = groupFileService.searchGroupfileByUserid(groupid,userid);
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "查询完成");
        data.put("data", list);
        return data;
    }
    @RequestMapping("/del")
    @ResponseBody
    public Map<String, Object> del(String id, int type) {

        Map<String, Object> data = new HashMap<>();
        groupFileService.deleteGroupFile(id,type);
        data.put("code", 0);
        data.put("msg", "删除完成");
        return data;
    }

}


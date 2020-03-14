package com.liangrui.hadoop_disk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liangrui.hadoop_disk.bean.dto.FileAndFolderDto;
import com.liangrui.hadoop_disk.bean.dto.FolderDto;
import com.liangrui.hadoop_disk.bean.dto.LayuiTree;
import com.liangrui.hadoop_disk.bean.entity.Resgroup;
import com.liangrui.hadoop_disk.bean.entity.Sharefile;
import com.liangrui.hadoop_disk.service.FileAndFolderService;
import com.liangrui.hadoop_disk.service.GroupFileService;
import com.liangrui.hadoop_disk.service.ShareService;
import com.liangrui.hadoop_disk.util.RowkeyUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/share")
public class shareController {
    @Autowired
    private ShareService shareService;
    @Autowired
    private GroupFileService groupFileService;

    @RequestMapping("/sharePage")
    public String sharePage(Model model )
    {
        return "share";
    }
    @RequestMapping("/addShareUrl")
    @ResponseBody
    public Map<String, Object> addShareUrl(@Param("objects") String objects, @Param("objects2") String objects2, HttpServletRequest httpServletRequest) {
        System.out.println(objects);
        System.out.println(objects2);
        int userid= (int) httpServletRequest.getSession().getAttribute("userid");
        Map<String, Object> data = new HashMap<>();
        JSONObject jsonObject= JSON.parseObject(objects2);
        Sharefile sharefile=JSON.toJavaObject(jsonObject,Sharefile.class);
        String rowkey=new RowkeyUtil().getRowkey();
        sharefile.setShareurl(rowkey);
        sharefile.setUserid(userid);

        List<FileAndFolderDto> list=JSONObject.parseArray(objects,FileAndFolderDto.class);
        shareService.addShare(list,sharefile);
        data.put("code", 0);
        data.put("msg", "添加完成");
        data.put("shareurl","http://localhost:8000/hadoop/share/s?shareUrl="+rowkey);
        data.put("password",sharefile.getPassword());
        return data;
    }
    @RequestMapping("/s")
    public String getshare(String shareUrl,Model model)
    {
        Sharefile sharefile=shareService.getfilebyShareurl(shareUrl);
        if(sharefile==null)
        {
            model.addAttribute("msg","云盘链接已经失踪");
            model.addAttribute("flag",0);
        }else{
            if(shareService.sharefileIsLowNewdate(sharefile.getStarttime(), sharefile.getSharetime())!=1)
            {
                model.addAttribute("msg","云盘链接已经过期");
                model.addAttribute("flag",0);
            }else {
                model.addAttribute("msg","云盘链接可以使用");
                model.addAttribute("flag",1);
            }
        }
        return "getfilefromurl";
    }
    @RequestMapping(value = "/treeload")
    @ResponseBody
    public Object findTypeTree(int groupid) throws Exception {
        Resgroup resgroup=shareService.findbygroupid(groupid);
        List<FolderDto> folderDtos = shareService.getFolderTree(groupid);//查出所有部门
        List<LayuiTree> list = new ArrayList<>();
        LayuiTree treeObject = new LayuiTree();

        treeObject.setId(resgroup.getRootfolder());
        treeObject.setName("全部文件");
        treeObject.setChildren(getChildren(resgroup.getRootfolder(), folderDtos));
        list.add(treeObject);
        return JSON.toJSON(list);
    }

    public List<LayuiTree> getChildren(String parentId, List<FolderDto> folderDtos) {
        List<LayuiTree> list = new ArrayList<>();
        for (FolderDto t : folderDtos) {
            if (t.getFatherfolderid().equals(parentId)) {
                LayuiTree treeObject = new LayuiTree();
                treeObject.setId(t.getFolderid().toString());
                treeObject.setName(t.getName());
                treeObject.setChildren(getChildren(t.getFolderid(), folderDtos));
                list.add(treeObject);
            }
        }
        return list;
    }
    @RequestMapping("/shareingroup")
    @ResponseBody
    public Map<String, Object> copyfileall( @Param("objects") String objects,@Param("groupid") Integer groupid,@Param("aimFolderid") String aimFolderid) {
        System.out.println(objects);
        System.out.println(groupid);

        Map<String, Object> rs = new HashMap<>();
        List<FileAndFolderDto> list=JSONObject.parseArray(objects,FileAndFolderDto.class);
            if (groupFileService.isexitNmaeinGrouplist(list, groupid,aimFolderid)) {
                rs.put("code", 1);
                rs.put("msg", "文件名不可重复");
            } else {

                   shareService.shareinGroup(list,groupid,aimFolderid);
                    rs.put("code", 0);
                    rs.put("msg", "复制成功");
            }
        return rs;
    }
    @RequestMapping("/groupfolder")
    public String groupfolder(int groupid,Model model)
    {
        model.addAttribute("groupid",groupid);
        return "groupFolder";
    }



}

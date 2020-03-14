package com.liangrui.hadoop_disk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liangrui.hadoop_disk.bean.dto.FriendUserDto;
import com.liangrui.hadoop_disk.bean.dto.GroupDto;
import com.liangrui.hadoop_disk.bean.entity.Diskuser;
import com.liangrui.hadoop_disk.bean.entity.Resgroup;
import com.liangrui.hadoop_disk.bean.model.layim.LayimChatModel;
import com.liangrui.hadoop_disk.bean.model.layim.LayinChatLogModel;
import com.liangrui.hadoop_disk.service.FriendAndGroupService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/friendAndGroup")
public class friendAndGroupController {
    @Autowired
    FriendAndGroupService friendAndGroupService;
    @RequestMapping("/friendDetail")
    public String friendDetail(Model model,int friendid)
    {
        Diskuser diskuser=friendAndGroupService.findDiskuserBy(friendid);
        model.addAttribute("diskuser",diskuser);
        return "userDetail";
    }
    @RequestMapping("/groupDetail")
    public String findGroupDetail(Model model,int groupid,HttpServletRequest httpServletRequest)
    {
        int userid= (int) httpServletRequest.getSession().getAttribute("userid");
        GroupDto groupDto=friendAndGroupService.findGroupdao(groupid,userid);
        model.addAttribute("groupDto",groupDto);
        return "GroupDetail";
    }
    @RequestMapping("/addMessage")
    @ResponseBody
    public Map<String, Object> addMessage(@Param("objects") String objects) {
        JSONObject jsonObject=JSON.parseObject(objects);
        LayimChatModel layimChatModel=JSON.toJavaObject(jsonObject,LayimChatModel.class);
        Map<String, Object> rs=new HashMap<>();
        int result = friendAndGroupService.saveChatMessage(layimChatModel);
        System.out.println(JSON.toJSONString(result));
        if (result == 0) {
            rs.put("code", 1);
            rs.put("msg", "添加失败");
        } else {
            rs.put("code", 0);
            rs.put("msg", "添加成功");
        }
        return rs;
    }
@RequestMapping("/addapply")
@ResponseBody
    public Map<String,Object> addapply(int toid, String message, String type,HttpServletRequest httpServletRequest)
    {
        int userid= (int) httpServletRequest.getSession().getAttribute("userid");
        Map<String, Object> rs=new HashMap<>();
        int result=friendAndGroupService.addApply(userid,toid,message,type);
        if (result == 0) {
            rs.put("code", 1);
            rs.put("msg", "添加失败");
        } else {
            rs.put("code", 0);
            rs.put("msg", "添加成功");
        }
        return rs;
    }
    @PostMapping("/addOrRejectFridend")
    @ResponseBody
    public Map<String,Object> addOrRejectFridend(int id,int type)//type=0 拒绝 type=1 同意
    {
        Map<String, Object> rs=new HashMap<>();
        rs.put("code", 0);
        rs.put("msg", "添加成功");
        friendAndGroupService.addOrRejectFridend(id,type);
        return rs;
    }
    @PostMapping("/addOrRejectGroup")
    @ResponseBody
    public Map<String,Object> addOrRejectGroup(int id,int type)//type=0 拒绝 type=1 同意
    {
        Map<String, Object> rs=new HashMap<>();
        rs.put("code", 0);
        rs.put("msg", "添加成功");
        friendAndGroupService.addOrRejectGroup(id,type);
        return rs;
    }

    @RequestMapping("/findallfriendDto")
    @ResponseBody
    public Map<String,Object> findallfriendDto(HttpServletRequest httpServletRequest)
    {
        int userid= (int) httpServletRequest.getSession().getAttribute("userid");
        Map<String, Object> rs=new HashMap<>();
        rs.put("code", 0);
        rs.put("msg", "添加成功");
        List<FriendUserDto> list=friendAndGroupService.fildAllfriendbyMasterid(userid);
        rs.put("data",list);
        return rs;
    }

    @RequestMapping("/findallResgroup")
    @ResponseBody
    public Map<String,Object> findallResgroup(HttpServletRequest httpServletRequest)
    {
        int userid= (int) httpServletRequest.getSession().getAttribute("userid");
        Map<String, Object> rs=new HashMap<>();
        rs.put("code", 0);
        rs.put("msg", "添加成功");
        List<GroupDto> list=friendAndGroupService.findAllResgroupByUserid(userid);
        rs.put("data",list);
        return rs;
    }
    @RequestMapping("/deletefriend")
    @ResponseBody
    public Map<String,Object> deletefriend(HttpServletRequest httpServletRequest,int frendid)
    {
        int userid= (int) httpServletRequest.getSession().getAttribute("userid");
        Map<String, Object> rs=new HashMap<>();
        int result=friendAndGroupService.deletefriend(userid,frendid);
        if(result!=0)
        {
            rs.put("code", 0);
            rs.put("msg", "删除成功");
        }else {
            rs.put("code", 1);
            rs.put("msg", "删除失败");
        }
        return rs;
    }
    @RequestMapping("/dropgroup")
    @ResponseBody
    public Map<String,Object> dropgroup(HttpServletRequest httpServletRequest,int groupid)
    {
        int userid= (int) httpServletRequest.getSession().getAttribute("userid");
        Map<String, Object> rs=new HashMap<>();
        int result=friendAndGroupService.dropResgroup(userid,groupid);
        if(result!=0)
        {
            rs.put("code", 0);
            rs.put("msg", "退出成功");
        }else {
            rs.put("code", 1);
            rs.put("msg", "退出失败");
        }
        return rs;
    }
    @RequestMapping("/cleargroup")
    @ResponseBody
    public Map<String,Object> cleargroup(int groupid)
    {
        Map<String, Object> rs=new HashMap<>();
        int result=friendAndGroupService.clearResgroup(groupid);
        rs.put("code", 0);
        rs.put("msg", "解散成功");
        return rs;
    }

    @RequestMapping("/addgroupindex")
    public String addgroupindex(Model model)
    {
        model.addAttribute("type",0);
        model.addAttribute("resgroup",new Resgroup());
        return "addgroup";
    }
    @RequestMapping("/updategroupindex")
    public String updategroupindex(Model model,int groupid)
    {
        model.addAttribute("type",1);
        model.addAttribute("resgroup",friendAndGroupService.getresgroupbyid(groupid));
        return "addgroup";
    }
    @RequestMapping("/addgroup")
    @ResponseBody
    public Map<String,Object> addgroup(@RequestBody Resgroup resgroup,HttpServletRequest httpServletRequest)
    {
        Map<String, Object> rs=new HashMap<>();
        int userid= (int) httpServletRequest.getSession().getAttribute("userid");
        resgroup.setUserid(userid);
        if(friendAndGroupService.existResgroupByName(resgroup.getName()))
        {
            rs.put("code", 1);
            rs.put("msg", "资源组名字不可以重复");
            return rs;
        }

        friendAndGroupService.creategroup(resgroup);
        rs.put("code", 0);
        rs.put("msg", "成功");
        return rs;
    }

    @RequestMapping("/updategroup")
    @ResponseBody
    public Map<String,Object> updategroup(@RequestBody Resgroup resgroup,int groupid)
    {
        Map<String, Object> rs=new HashMap<>();
        friendAndGroupService.updategroup(resgroup,groupid);
        rs.put("code", 0);
        rs.put("msg", "成功");
        return rs;
    }






}

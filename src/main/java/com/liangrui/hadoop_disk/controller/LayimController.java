package com.liangrui.hadoop_disk.controller;

import com.alibaba.fastjson.JSON;
import com.liangrui.hadoop_disk.bean.model.layim.*;
import com.liangrui.hadoop_disk.service.LayimService;
import org.apache.avro.data.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/layim")
public class LayimController {
    @Autowired
    LayimService layimService;
    @RequestMapping("/init")
    @ResponseBody
    public Map<String,Object> init(int userid)
    {
        Map<String,Object> map=new HashMap<>();
        map.put("code",0);
        map.put("msg","");
        map.put("data",layimService.initlayim(userid));
        System.out.println(JSON.toJSONString(map));
        return map;
    }

    @RequestMapping("/getNoReadMessage")
    @ResponseBody
    public Map<String, Object> getNoReadMessage(HttpServletRequest httpServletRequest)
    {
        int userid= (int) httpServletRequest.getSession().getAttribute("userid");
        System.out.println("sfds "+userid);
        Map<String, Object> rs=new HashMap<>();
        List<LayimChatModel> list=layimService.getNoReadMessage(userid);
        rs.put("code", 0);
        rs.put("msg", "添加成功");
        rs.put("data",list);
        System.out.println(JSON.toJSONString(rs));
        return rs;
    }
    @RequestMapping("/getOldMsgs")
    @ResponseBody
    public Map<String, Object> getOldMessage(int pageNo,int pageSize,int toid,String type,HttpServletRequest httpServletRequest)
    {
        int userid= (int) httpServletRequest.getSession().getAttribute("userid");
        List<LayinChatLogModel> list=layimService.getOldMessage(pageNo,pageSize,toid,type,userid);
        Map<String, Object> rs=new HashMap<>();
        rs.put("code", 0);
        rs.put("msg", "添加成功");
        rs.put("data",list);
        System.out.println(JSON.toJSONString(list));
        return rs;
    }
    @RequestMapping("/search")
    @ResponseBody
    public Map<String, Object> search(String name)
    {
        Map<String, Object> rs=new HashMap<>();
        List<LayimUserModel> diskusers=layimService.searchUserbyname(name);
        List<LayimGroupModel> resgroups=layimService.searchGroupbyname(name);
        rs.put("users",diskusers);
        rs.put("groups",resgroups);
        return rs;
    }
    @RequestMapping("/getmsgbox")
    @ResponseBody
    public Map<String, Object> getmsgbox(HttpServletRequest httpServletRequest)
    {
        Map<String, Object> rs=new HashMap<>();
        int userid= (int) httpServletRequest.getSession().getAttribute("userid");
        List<LayimMsgboxModel> list=layimService.getMsgbox(userid);
        rs.put("code", 0);
        rs.put("pages", 1);
        rs.put("data",list);
        return rs;
    }

    @RequestMapping("/getnumbers")
    @ResponseBody
    public Map<String, Object> getnumbers(int id)
    {
        Map<String, Object> rs=new HashMap<>();
        List<LayimUserModel> list=layimService.getnumberbygroupid(id);
        rs.put("code", 0);
        Map<String,Object> war = new HashMap<String,Object>();
        war.put("list", list);
        rs.put("data",war);
        return rs;
    }
}

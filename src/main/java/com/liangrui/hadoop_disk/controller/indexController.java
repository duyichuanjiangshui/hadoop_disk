package com.liangrui.hadoop_disk.controller;

import com.liangrui.hadoop_disk.bean.dto.FriendUserDto;
import com.liangrui.hadoop_disk.bean.dto.GroupDto;
import com.liangrui.hadoop_disk.bean.entity.Friend;
import com.liangrui.hadoop_disk.bean.entity.Resgroup;
import com.liangrui.hadoop_disk.service.FriendAndGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.util.List;

@Controller
@RequestMapping("/index")
public class indexController {
    @Autowired
    FriendAndGroupService friendAndGroupService;
    @RequestMapping("/mainindex")
    public String mainIndex()
    {
        return "iframe";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @RequestMapping("/test")
    public String test()
    {
        return "mychat";
    }
    @RequestMapping("/find")
    public String findpage(Model model,int userid)
    {
        model.addAttribute("userid",userid);
        return "find";
    }
    @RequestMapping("/moreMenu")
    public String creteGroup()
    {
        return "moreMenu";
    }

    @RequestMapping("/friend")
    public String getfriendPage(Model model, HttpServletRequest httpServletRequest)
    {
        int userid= (int) httpServletRequest.getSession().getAttribute("userid");
        System.out.println(userid);
       List<FriendUserDto> friendUserDtoList =friendAndGroupService.fildAllfriendbyMasterid(userid);
       List<GroupDto> groupDtoList=friendAndGroupService.findAllResgroupByUserid(userid);
       model.addAttribute("friendUserDtoList",friendUserDtoList);
       model.addAttribute("groupDtoList",groupDtoList);
       model.addAttribute("currentUserId",userid);
        return "friend";
    }
    @RequestMapping("/chatlog")
    public String chatlog()
    {
        return "chatlog";
    }
    @RequestMapping("/msgbox")
    public String msgbox()
    {
        return "msgbox";
    }



}

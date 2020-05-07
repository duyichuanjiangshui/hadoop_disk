package com.liangrui.hadoop_disk.controller;

import com.liangrui.hadoop_disk.bean.dto.FriendUserDto;
import com.liangrui.hadoop_disk.bean.dto.GroupDto;
import com.liangrui.hadoop_disk.bean.entity.Diskuser;
import com.liangrui.hadoop_disk.bean.entity.Friend;
import com.liangrui.hadoop_disk.bean.entity.Resgroup;
import com.liangrui.hadoop_disk.service.FriendAndGroupService;
import com.liangrui.hadoop_disk.service.UserService;
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
    @Autowired
    private UserService userService;
    @RequestMapping("/mainindex")
    public String mainIndex(Model model,HttpServletRequest httpServletRequest)
    {
      int userid=  (int) httpServletRequest.getSession().getAttribute("userid");
        Diskuser diskuser1=userService.finddiskuserbyuserid(userid);
        model.addAttribute("userid",userid);
        model.addAttribute("fatherFolderid",diskuser1.getRootfolderid());
        model.addAttribute("name",diskuser1.getName());
        model.addAttribute("imgsrc",diskuser1.getImgpath());
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
    @RequestMapping("/register")
    public String registerpage()
    {
        return "register";
    }
    @RequestMapping("/forgetpassword")
    public String forgetpassword()
    {
        return "getbackpassword";
    }

    @RequestMapping("mydetail")
    public String mydetail(Model model,HttpServletRequest httpServletRequest)
    {
        int userid=  (int) httpServletRequest.getSession().getAttribute("userid");
        Diskuser diskuser1=userService.finddiskuserbyuserid(userid);
        model.addAttribute("user",diskuser1);
        return "mydetail";
    }
    @RequestMapping("newpwd")
    public String newpwd()
    {
        return  "newpassword";
    }
    @RequestMapping("/error")
    public String error()
    {
        return "error";
    }
    @RequestMapping("/quickclean")
    public String quickkclean()
    {
        return "quickclean";
    }




}

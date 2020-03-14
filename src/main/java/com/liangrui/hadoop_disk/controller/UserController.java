package com.liangrui.hadoop_disk.controller;



import com.liangrui.hadoop_disk.bean.entity.Diskuser;
import com.liangrui.hadoop_disk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @RequestMapping("/login")
    public String getUserInfo(Diskuser diskuser, Model model,  HttpServletRequest httpServletRequest) {

        Diskuser diskuser1=userService.userLogin(diskuser);
            if(diskuser1!=null) {
                httpServletRequest.getSession().setAttribute("userid",diskuser1.getUserid());
                httpServletRequest.getSession().setAttribute("rootFolderid",diskuser1.getRootfolderid());

                System.out.println(httpServletRequest.getSession().getAttribute("userid"));
                model.addAttribute("userid",diskuser1.getUserid());
                model.addAttribute("fatherFolderid",diskuser1.getRootfolderid());
                return "iframe";
            }else {
                model.addAttribute("flag", "fail");
                return "login";
            }
        }
}
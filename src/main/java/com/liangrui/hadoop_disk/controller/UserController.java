package com.liangrui.hadoop_disk.controller;



import com.alibaba.fastjson.JSONObject;
import com.liangrui.hadoop_disk.bean.entity.Diskuser;
import com.liangrui.hadoop_disk.service.UserService;
import com.liangrui.hadoop_disk.util.MD5Util;
import com.liangrui.hadoop_disk.util.SendMail;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;


import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object>  getUserInfo(@RequestBody Diskuser diskuser, HttpServletRequest httpServletRequest) {
        Map<String, Object> rs = new HashMap<>();
        Diskuser diskuser1=userService.userLogin(diskuser.getName());
            if(diskuser1!=null) {
                if(MD5Util.isPwdRight(diskuser.getPassword(),diskuser1.getPassword()))
                {
                    httpServletRequest.getSession().setAttribute("userid",diskuser1.getUserid());
                    httpServletRequest.getSession().setAttribute("rootFolderid",diskuser1.getRootfolderid());
                    userService.updatestatue(diskuser1.getUserid(),1);
                    rs.put("code",0);
                    return rs;
                }

                rs.put("code",1);
                rs.put("message","密码不正确");
                return rs;
            }else {
                rs.put("code",1);
                rs.put("message","用户名不正确");
                return rs;
            }
        }
        @RequestMapping("/haveemail")
    @ResponseBody
    public int haveemail(String email)
    {
        return userService.haveemail(email);
    }
    @RequestMapping("/havename")
    @ResponseBody
    public int havename(String user)
    {
        return userService.havename(user);
    }
        @RequestMapping("/register")
         @ResponseBody
        public Map<String, Object> register( Diskuser diskuser)
        {
            Map<String, Object> rs = new HashMap<>();
            if(userService.adddiskuser(diskuser)==0)
            {
                rs.put("code",1);

            }else {
                rs.put("code",0);
            }
            return  rs;
        }
    //找回密码控制器
    @RequestMapping("/sendemail")
    @ResponseBody // 此注解不能省略 否则ajax无法接受返回值
    public Map<String, Object>  retrievePassword(String email)
            throws UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<String, Object>();

        //实例化一个发送邮件的对象
        SendMail mySendMail = new SendMail();
        //修改密码并返回
        //产生随机的6位数密码
        int Password = ((int)((Math.random()*9+1)*100000));
        //根据邮箱寻找是否有该用户信息，找到就修改密码，否则就提示用户无效的用户Email，此步代码省略。。。。
        //修改密码成功后进行发送邮件
        //设置收件人和消息内容
        mySendMail.sendMail(email, "友情提醒，您的密码为："+ Password);
        map.put("code", 200);
        map.put("msg", "恭喜，找回密码成功，请前往邮箱查看密码！");
        map.put("mypassword", MD5Util.encodePwd(String.valueOf(Password)));
        System.out.println(MD5Util.encodePwd(String.valueOf(Password)));
        return map;
    }
    @RequestMapping("/verify")
    @ResponseBody // 此注解不能省略 否则ajax无法接受返回值
    public Map<String, Object>  verify(String verify,String password) {
        Map<String, Object> map = new HashMap<String, Object>();

        if(MD5Util.isPwdRight(password,verify))
        map.put("code", 0);
        else
            map.put("code",1);
        return map;
    }
    @RequestMapping("/updatepassword")
    @ResponseBody // 此注解不能省略 否则ajax无法接受返回值
    public Map<String, Object>  updatepassword(String email,String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        int code=userService.updatepassword(password,email);
        map.put("code",code);

        return map;
    }
    @RequestMapping("/updatemydetail")
    @ResponseBody
    public Map<String, Object> updatemydetail(@RequestBody Diskuser diskuser)
    {
        Map<String, Object> rs = new HashMap<>();
        if(userService.updatediskuser(diskuser)==0)
        {
            rs.put("code",1);

        }else {
            rs.put("code",0);
        }
        return  rs;
    }
    @RequestMapping("/updatepwd")
    @ResponseBody
    public Map<String, Object> updatepwd(HttpServletRequest httpServletRequest,String orignpassword,String password)
    {
        Map<String, Object> rs = new HashMap<>();
        int userid= (int) httpServletRequest.getSession().getAttribute("userid");
        if(userService.updatepwd(userid,orignpassword,password)==0)
        {
            rs.put("code",1);
        }else {
            rs.put("code",0);
        }
        return  rs;
    }
    @RequestMapping("/loginout")
    public String loginout(HttpServletRequest httpServletRequest,SessionStatus sessionStatus)
    {
        HttpSession session=httpServletRequest.getSession();
        int userid= (int)session.getAttribute("userid");
        userService.updatestatue(userid,0);
        session.invalidate();
        sessionStatus.setComplete();
        return "login";
    }
    @RequestMapping("/updatesign")
    @ResponseBody
    public Map<String, Object> updatesign(HttpServletRequest httpServletRequest,String sign)
    {
        Map<String, Object> rs = new HashMap<>();
        int userid= (int) httpServletRequest.getSession().getAttribute("userid");
        if(userService.updatesign(userid,sign)==0)
        {
            rs.put("code",1);
        }else {
            rs.put("code",0);
        }
        return  rs;
    }

}
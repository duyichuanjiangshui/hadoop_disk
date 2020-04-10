package com.liangrui.hadoop_disk.service.impl;

import com.liangrui.hadoop_disk.bean.entity.Diskuser;
import com.liangrui.hadoop_disk.mapper.DiskuserMapper;
import com.liangrui.hadoop_disk.service.UserService;
import com.liangrui.hadoop_disk.util.DateUtil;
import com.liangrui.hadoop_disk.util.MD5Util;
import com.liangrui.hadoop_disk.util.RowkeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: xincheng.zhao
 * @date: 2018/8/2
 * @description:
 */
@Service
public class UserServiceImpl implements UserService {
   @Autowired
   DiskuserMapper diskuserMapper;
    /*
     *用户登录
     */

    @Override
    public Diskuser userLogin(String name) {

        return diskuserMapper.selectbyname(name);
    }

    @Override
    public Diskuser finddiskuserbyuserid(int userid) {
        return diskuserMapper.selectByPrimaryKey(userid);
    }

    @Override
    public int havename(String name) {
        Integer result=diskuserMapper.getuserId(name);
        if(result==null)
             return 0;
        else
             return 1;
    }

    @Override
    public int updatepassword(String password, String email) {
        return diskuserMapper.updatepassword(password,email);
    }

    @Override
    public int updatestatue(int userid, int n) {
        Diskuser diskuser=new Diskuser();
        diskuser.setStatue(n);
        diskuser.setUserid(userid);
        return diskuserMapper.updateByPrimaryKey(diskuser);
    }

    @Override
    public int adddiskuser(Diskuser diskuser) {
        diskuser.setCreatetime(DateUtil.DateToString("yyyy-MM-dd HH:mm:ss",new Date()));
        diskuser.setRootfolderid(new RowkeyUtil().getRowkey());
        diskuser.setPassword(MD5Util.encodePwd(diskuser.getPassword()));//加密
        return diskuserMapper.insert(diskuser);

    }

    @Override
    public int haveemail(String email) {
        Integer result=diskuserMapper.getuserIdbyemail(email);
        if(result==null)
            return 0;
        else
            return 1;
    }

    @Override
    public int updatediskuser(Diskuser diskuser) {
        return diskuserMapper.updateByPrimaryKey(diskuser);
    }

    @Override
    public int updatepwd(int userid, String orignpassword, String password) {
        String myorignpassword=diskuserMapper.getpasswordbydiskuser(userid);
        System.out.println(myorignpassword+'\n'+orignpassword);
        if(!orignpassword.equals(myorignpassword))
            return 0;
        Diskuser diskuser1=new Diskuser();
        diskuser1.setUserid(userid);
        diskuser1.setPassword(password);
        return diskuserMapper.updateByPrimaryKey(diskuser1);
    }

    @Override
    public int logoutstatue(int userid) {
        Diskuser diskuser1=new Diskuser();
        diskuser1.setUserid(userid);
        diskuser1.setStatue(0);
        return diskuserMapper.updateByPrimaryKey(diskuser1);
    }

    @Override
    public int updatesign(int userid, String sign) {
        Diskuser diskuser1=new Diskuser();
        diskuser1.setUserid(userid);
        diskuser1.setSign(sign);
        return diskuserMapper.updateByPrimaryKey(diskuser1);
    }
}



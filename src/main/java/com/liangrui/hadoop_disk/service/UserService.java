package com.liangrui.hadoop_disk.service;

import com.liangrui.hadoop_disk.bean.entity.Diskuser;
/**
 * Created with IntelliJ IDEA.
 *
 * @author: xincheng.zhao
 * @date: 2018 /8/2
 * @description:
 */
public interface UserService {

    Diskuser userLogin(String name);
    Diskuser finddiskuserbyuserid(int userid);
    int havename(String name);
    int haveemail(String email);
    int adddiskuser(Diskuser diskuser);
    int updatepassword(String password,String email);
    int updatediskuser(Diskuser diskuser);
    int updatepwd(int userid,String orignpassword,String password);
    int logoutstatue(int userid);
    int updatestatue(int userid,int n);
    int updatesign(int userid,String sign);
}

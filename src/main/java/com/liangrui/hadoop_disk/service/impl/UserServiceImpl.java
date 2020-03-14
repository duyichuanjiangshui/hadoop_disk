package com.liangrui.hadoop_disk.service.impl;

import com.liangrui.hadoop_disk.bean.entity.Diskuser;
import com.liangrui.hadoop_disk.mapper.DiskuserMapper;
import com.liangrui.hadoop_disk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Diskuser userLogin(Diskuser diskuser) {

        return diskuserMapper.selectbyname(diskuser.getName());
    }


}



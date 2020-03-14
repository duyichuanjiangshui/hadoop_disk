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

    Diskuser userLogin(Diskuser diskuser);
}

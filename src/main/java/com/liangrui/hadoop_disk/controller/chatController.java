package com.liangrui.hadoop_disk.controller;

import com.liangrui.hadoop_disk.bean.dto.FriendUserDto;
import com.liangrui.hadoop_disk.bean.dto.GroupDto;
import com.liangrui.hadoop_disk.bean.dto.GroupUserDto;
import com.liangrui.hadoop_disk.bean.entity.Diskuser;
import com.liangrui.hadoop_disk.service.FriendAndGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/chat")
public class chatController  {
    @Autowired
    FriendAndGroupService friendAndGroupService;
}

package com.liangrui.hadoop_disk.service;


import com.liangrui.hadoop_disk.bean.dto.FriendUserDto;
import com.liangrui.hadoop_disk.bean.dto.GroupDto;
import com.liangrui.hadoop_disk.bean.entity.Diskuser;
import com.liangrui.hadoop_disk.bean.entity.Resgroup;
import com.liangrui.hadoop_disk.bean.model.layim.LayimChatModel;

import java.util.List;


public interface FriendAndGroupService {
    List<FriendUserDto> fildAllfriendbyMasterid(int masterid);
    List<GroupDto> findAllResgroupByUserid(int userid);
    Diskuser findDiskuserBy(int userid);
    GroupDto findGroupdao(int groupid,int userid);
    int saveChatMessage(LayimChatModel layimChatModel);
    int  addApply(int userid,int toid,String message,String type);
    void addOrRejectFridend(int id, int type);
    void addOrRejectGroup(int id, int type);
    boolean friendIsNull(int toid,int fromid);
    boolean groupnumberidNUll(int groupid,int userid);
    int deletefriend(int userid,int friendid);
    int dropResgroup(int userid,int groupid);
    int clearResgroup(int groupid);
    int creategroup(Resgroup resgroup);
    int updategroup(Resgroup resgroup,int groupid);
    Resgroup getresgroupbyid(int groupid);
    boolean existResgroupByName(String name);


}

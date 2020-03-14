package com.liangrui.hadoop_disk.service.impl;

import com.liangrui.hadoop_disk.bean.dto.FriendUserDto;
import com.liangrui.hadoop_disk.bean.dto.GroupDto;
import com.liangrui.hadoop_disk.bean.entity.*;
import com.liangrui.hadoop_disk.bean.model.layim.LayimChatModel;
import com.liangrui.hadoop_disk.handler.MyHandler;
import com.liangrui.hadoop_disk.mapper.*;

import com.liangrui.hadoop_disk.service.FriendAndGroupService;
import com.liangrui.hadoop_disk.util.DateUtil;
import com.liangrui.hadoop_disk.util.RowkeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class FriendAndGroupServiceImpl implements FriendAndGroupService {
    @Autowired
    DiskuserMapper diskuserMapper;
    @Autowired
    FriendMapper friendMapper;
    @Autowired
    ResgroupMapper resgroupMapper;
    @Autowired
    GroupmessageMapper groupmessageMapper;
    @Autowired
    GroupnumberMapper groupnumberMapper;
    @Autowired
    UsermessageMapper usermessageMapper;
    @Autowired
    AddapplyMapper addapplyMapper;

    @Override
    public List<FriendUserDto> fildAllfriendbyMasterid(int masterid) {
        List<Friend> list=friendMapper.findbymasterid(masterid);
        List<FriendUserDto> list1=new ArrayList<>();
        for(Friend friend:list)
        {
            FriendUserDto friendUserDto=new FriendUserDto();
            friendUserDto.setFriendid(friend.getFriendid());
            Diskuser diskuser=diskuserMapper.selectByPrimaryKey(friend.getFriendid());
            if(diskuser.getImgpath() == null)
            {
                friendUserDto.setImgsrc("../static/images/dfdg.jpg");
            }else {
                friendUserDto.setImgsrc(diskuser.getImgpath());
            }
            friendUserDto.setStatue(diskuser.getStatue());

            friendUserDto.setName(diskuser.getName());
            friendUserDto.setSign(diskuser.getSign());
            list1.add(friendUserDto);
        }
        return list1 ;
    }

    @Override
    public List<GroupDto> findAllResgroupByUserid(int userid) {
        List<Groupnumber> list= groupnumberMapper.findAllGroupnumberbyuserid(userid);
        List<GroupDto> list1=new ArrayList<>();
        for(Groupnumber groupnumber:list)
        {
            GroupDto groupDto=new GroupDto();
            groupDto.setGroupid(groupnumber.getGroupid());

            Resgroup resgroup=resgroupMapper.selectByPrimaryKey(groupnumber.getGroupid());
            if(resgroup.getUserid()==userid)
            {
                groupDto.setIsowner(1);
            }else {
                groupDto.setIsowner(0);
            }
            if(resgroup.getImgpath() == null)
            {
                groupDto.setImgsrc("../static/images/dfdg.jpg");
            }else {
                groupDto.setImgsrc(resgroup.getImgpath());
            }
            groupDto.setName(resgroup.getName());
            list1.add(groupDto);
        }
        return list1;
    }

    @Override
    public Diskuser findDiskuserBy(int userid) {

        Diskuser diskuser;
        diskuser = diskuserMapper.selectByPrimaryKey(userid);
        return diskuser;
    }

    @Override
    public GroupDto findGroupdao(int groupid,int userid) {
        System.out.println(userid);
        GroupDto groupDto=new GroupDto();
        Resgroup resgroup=resgroupMapper.selectByPrimaryKey(groupid);
        if(userid==resgroup.getUserid())
            groupDto.setIsowner(1);
        else
        groupDto.setIsowner(0);
        groupDto.setRootfolder(resgroup.getRootfolder());
        groupDto.setImgsrc(resgroup.getImgpath());
        groupDto.setName(resgroup.getName());
        groupDto.setGroupid(groupid);
        return groupDto;
    }

    @Override
    public int saveChatMessage(LayimChatModel layimChatModel) {
        if(layimChatModel.getType().equals("friend"))
        {
            Usermessage usermessage=new Usermessage();
            if(MyHandler.userSocketSessionMap.get(layimChatModel.getToId())==null)
            usermessage.setStatue(0);//标记离线
            else
                usermessage.setStatue(1);//标记已读
            usermessage.setFromuserid(layimChatModel.getId());
            usermessage.setTime(DateUtil.DateToString("yyyy-MM-dd HH:mm:ss",layimChatModel.getTimestamp()));
            usermessage.setTouserid(layimChatModel.getToId());
            usermessage.setMessage(layimChatModel.getContent());
           return usermessageMapper.insert(usermessage);
        }else{
            Groupmessage groupmessage=new Groupmessage();
            groupmessage.setFormid(layimChatModel.getId());
            groupmessage.setGroupid(layimChatModel.getToId());
            groupmessage.setTime(DateUtil.DateToString("yyyy-MM-dd HH:mm:ss",layimChatModel.getTimestamp()));
            groupmessage.setMessage(layimChatModel.getContent());
            return groupmessageMapper.insert(groupmessage);
        }
    }

    @Override

    public int addApply(int userid, int toid, String message, String type) {
        Addapply addapply=new Addapply();
        addapply.setMessage(message);
        addapply.setFormid(userid);
        /*
         * statue 有 1 2 3 4
         * 1 是发送给好友
         * 4 是发送给群组
         * 2 是拒绝
         * 3 是同意
         * */

        if(type.equals("friend"))
        {
            addapply.setStatue(1);
            addapply.setToid(toid);
        }else{
            addapply.setStatue(4);
            Resgroup resgroup=resgroupMapper.selectByPrimaryKey(toid);
            addapply.setToid(resgroup.getUserid());
            addapply.setGroupid(toid);
        }
        addapply.setTime(DateUtil.DateToString("yyyy-MM-dd HH:mm:ss",new Date()));
        return addapplyMapper.insert(addapply);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void addOrRejectFridend(int id, int type) {
      Addapply addapply= addapplyMapper.selectByPrimaryKey(id);
      if(type==0)
      {
          addapply.setStatue(2);
      }else {
          if(friendIsNull(addapply.getFormid(),addapply.getToid()))
          {
              Friend friend=new Friend();
              Friend friend1=new Friend();
              friend.setMasterid(addapply.getFormid());
              friend.setFriendid(addapply.getToid());
              friend1.setMasterid(addapply.getToid());
              friend1.setFriendid(addapply.getFormid());
              String time=DateUtil.DateToString("yyyy-MM-dd HH:mm:ss",new Date());
              friend.setEstablishtime(time);
              friend1.setEstablishtime(time);
              friendMapper.insert(friend);
              friendMapper.insert(friend1);
          }
          addapply.setStatue(3);
          addapplyMapper.updateByPrimaryKey(addapply);
      }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void addOrRejectGroup(int id, int type) {
        Addapply addapply= addapplyMapper.selectByPrimaryKey(id);
        if(type==0)
        {
            addapply.setStatue(2);
            addapplyMapper.updateByPrimaryKey(addapply);
        }else {
            addapply.setStatue(3);
            if(groupnumberidNUll(addapply.getGroupid(),addapply.getFormid()))
            {
                String time=DateUtil.DateToString("yyyy-MM-dd HH:mm:ss",new Date());
                Groupnumber groupnumber=new Groupnumber();
                groupnumber.setGroupid(addapply.getGroupid());
                groupnumber.setJointime(time);
                groupnumber.setUserid(addapply.getFormid());
                groupnumberMapper.insert(groupnumber);
            }
            addapplyMapper.updateByPrimaryKey(addapply);
        }
    }

    @Override
    public boolean friendIsNull(int toid,int fromid) {
        Integer result=friendMapper.findbyfriendid(fromid,toid);
        return result==null;
    }

    @Override
    public boolean groupnumberidNUll(int groupid,int userid) {
        Integer result=groupnumberMapper.findbygrouidanduserid(groupid,userid);
        return result==null;
    }

    @Override
    public int deletefriend(int userid, int friendid) {
        return friendMapper.deletefriend(friendid,userid);
    }

    @Override
    public int dropResgroup(int userid, int groupid) {
        return groupnumberMapper.deleteGroupUser(groupid,userid);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public int clearResgroup( int groupid) {
        groupnumberMapper.deleteAllGroupUser(groupid);
        groupmessageMapper.deleteGroupmessage(groupid);
        addapplyMapper.deleteByGroupid(groupid);
        resgroupMapper.deleteByPrimaryKey(groupid);
        return 0;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public int creategroup(Resgroup resgroup) {
        if(resgroup.getImgpath()==null)
            resgroup.setImgpath("/hadoop/static/images/dfdg.jpg");
        String time=DateUtil.DateToString("yyyy-MM-dd HH:mm:ss",new Date());
        resgroup.setCreatetime(time);
        resgroup.setRootfolder(new RowkeyUtil().getRowkey());
        int result=resgroupMapper.insert(resgroup);
        Resgroup resgroup1=resgroupMapper.selectresgroupbyname(resgroup.getName());
        Groupnumber groupnumber=new Groupnumber();
        groupnumber.setUserid(resgroup.getUserid());
        groupnumber.setJointime(time);
        groupnumber.setGroupid(resgroup1.getGroupid());
        groupnumberMapper.insert(groupnumber);
        return result;
    }

    @Override
    public int updategroup(Resgroup resgroup,int groupid) {
        Resgroup resgroup1=resgroupMapper.selectByPrimaryKey(groupid);
        resgroup.setCreatetime(resgroup1.getCreatetime());
        resgroup.setGroupid(resgroup1.getGroupid());
        resgroup.setUserid(resgroup1.getUserid());
        return resgroupMapper.updateByPrimaryKey(resgroup);
    }

    @Override
    public boolean existResgroupByName(String name) {
        return resgroupMapper.selectresgroupbyname(name)!=null;
    }

    @Override
    public Resgroup getresgroupbyid(int groupid) {
        return resgroupMapper.selectByPrimaryKey(groupid);
    }
}

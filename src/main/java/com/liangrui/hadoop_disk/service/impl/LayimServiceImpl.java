package com.liangrui.hadoop_disk.service.impl;

import com.liangrui.hadoop_disk.bean.dto.FriendUserDto;
import com.liangrui.hadoop_disk.bean.dto.GroupDto;
import com.liangrui.hadoop_disk.bean.entity.*;
import com.liangrui.hadoop_disk.bean.model.layim.*;
import com.liangrui.hadoop_disk.mapper.*;
import com.liangrui.hadoop_disk.service.FriendAndGroupService;
import com.liangrui.hadoop_disk.service.LayimService;
import com.liangrui.hadoop_disk.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class LayimServiceImpl implements LayimService {
    @Autowired
    DiskuserMapper diskuserMapper;
    @Autowired
    FriendAndGroupService friendAndGroupService;
    @Autowired
    GroupmessageMapper groupmessageMapper;
    @Autowired
    GroupnumberMapper groupnumberMapper;
    @Autowired
    UsermessageMapper usermessageMapper;
    @Autowired
    ResgroupMapper resgroupMapper;
    @Autowired
    AddapplyMapper addapplyMapper;
    @Override
    public InitDataModel initlayim(int userid) {
        InitDataModel initDataModel=new InitDataModel();
        Diskuser diskuser=diskuserMapper.selectByPrimaryKey(userid);
        LayimUserModel mine=new LayimUserModel();
        mine.setAvatar(diskuser.getImgpath());
        mine.setId(diskuser.getUserid());
        if(diskuser.getStatue()==1)//登录状态
        mine.setStatus("online");
        mine.setUsername(diskuser.getName());
        mine.setSign(diskuser.getSign());
        initDataModel.setMine(mine);
        LayimFriendSMoldel layimFriendSMoldel=new LayimFriendSMoldel();
        //只有一组
        layimFriendSMoldel.setGroupname("全部好友");
        layimFriendSMoldel.setId(1);
        layimFriendSMoldel.setOnline(1);
        //添加一个好友组的好友
        List<LayimFriendSMoldel> layimFriendSMoldels=new ArrayList<>();
        List<LayimUserModel>  layimUserModels1=new ArrayList<>();
        List<FriendUserDto> friendUserDtoList=friendAndGroupService.fildAllfriendbyMasterid(userid);
        for(FriendUserDto friendUserDto:friendUserDtoList)
        {
            LayimUserModel layimUserModel=new LayimUserModel();
            if(friendUserDto.getStatue()==1)//登录状态
               layimUserModel.setStatus("online");
            layimUserModel.setId(friendUserDto.getFriendid());
            layimUserModel.setSign(friendUserDto.getSign());
            layimUserModel.setAvatar(friendUserDto.getImgsrc());
            layimUserModel.setUsername(friendUserDto.getName());
            layimUserModels1.add(layimUserModel);
        }
        layimFriendSMoldel.setList(layimUserModels1);
        layimFriendSMoldels.add(layimFriendSMoldel);
        initDataModel.setFriend(layimFriendSMoldels);
        //设置群组
        List<GroupDto> list=friendAndGroupService.findAllResgroupByUserid(userid);
        List<LayimGroupModel> layimGroupModelList=new ArrayList<>();
        for(GroupDto groupDto:list)
        {
            LayimGroupModel layimGroupModel=new LayimGroupModel();
            layimGroupModel.setAvatar(groupDto.getImgsrc());
            layimGroupModel.setGroupname(groupDto.getName());
            layimGroupModel.setId(groupDto.getGroupid());
            layimGroupModelList.add(layimGroupModel);
        }
        initDataModel.setGroup(layimGroupModelList);
        return initDataModel;
    }

    /*
    * username: "纸飞机" //消息来源用户名
  ,avatar: "http://tp1.sinaimg.cn/1571889140/180/40030060651/1" //消息来源用户头像
  ,id: "100000" //消息的来源ID（如果是私聊，则是用户id，如果是群聊，则是群组id）
  ,type: "friend" //聊天窗口来源类型，从发送消息传递的to里面获取
  ,content: "嗨，你好！本消息系离线消息。" //消息内容
  ,cid: 0 //消息id，可不传。除非你要对消息进行一些操作（如撤回）
  ,mine: false //是否我发送的消息，如果为true，则会显示在右方
  ,fromid: "100000" //消息的发送者id（比如群组中的某个消息发送者），可用于自动解决浏览器多窗口时的一些问题
  ,timestamp: 1467475443306 //服务端时间戳毫秒数。注意：如果你返回的是标准的 unix 时间戳，记得要 *1000
    * */
    @Override
    public List<LayimChatModel> getNoReadMessage(int userid) {
        List<Usermessage> list=usermessageMapper.findnoreadmessage(userid);
        List<LayimChatModel> layimChatModelList=new ArrayList<>();
        for(Usermessage usermessage:list)
        {
            usermessage.setStatue(1);
            usermessageMapper.updateByPrimaryKey(usermessage);
            LayimChatModel layimChatModel=new LayimChatModel();
            Diskuser diskuser=diskuserMapper.selectByPrimaryKey(usermessage.getFromuserid());
            layimChatModel.setId(diskuser.getUserid());
            layimChatModel.setUsername(diskuser.getName());
            layimChatModel.setAvatar(diskuser.getImgpath());
            layimChatModel.setType("friend");
            layimChatModel.setContent(usermessage.getMessage());
            layimChatModel.setMine(false);
            layimChatModel.setToId(userid);
            try {
                layimChatModel.setTimestamp(DateUtil.StringToDate("yyyy-MM-dd HH:mm:ss",usermessage.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            layimChatModelList.add(layimChatModel);
        }
        return layimChatModelList;
    }

    @Override
    public List<LayinChatLogModel> getOldMessage(int pageNo, int pageSize, int toid, String type, int userid) {
        List<LayinChatLogModel> layinChatLogModels=new ArrayList<>();
        if(type.equals("friend"))
        {
            Diskuser diskuser1=diskuserMapper.selectByPrimaryKey(userid);
            Diskuser diskuser2=diskuserMapper.selectByPrimaryKey(toid);
            List<Usermessage> list=usermessageMapper.findchatlog((pageNo-1)*pageSize,pageSize,toid,userid);

            for(Usermessage usermessage:list)
            {
                LayinChatLogModel layinChatLogModel=new LayinChatLogModel();
                if(usermessage.getFromuserid()==userid)
                {
                    layinChatLogModel.setAvatar(diskuser1.getImgpath());
                    layinChatLogModel.setUsername(diskuser1.getName());
                }
                else
                {
                    layinChatLogModel.setUsername(diskuser2.getName());
                    layinChatLogModel.setAvatar(diskuser2.getImgpath());
                }
                layinChatLogModel.setContent(usermessage.getMessage());
                layinChatLogModel.setId(usermessage.getFromuserid());
                layinChatLogModel.setTimestamp(usermessage.getTime());
                layinChatLogModels.add(layinChatLogModel);

            }
        }else
        {
            List<Groupmessage> list=groupmessageMapper.findchatlog((pageNo-1)*pageSize,pageSize,toid);

            for(Groupmessage groupmessage:list)
            {
                LayinChatLogModel layinChatLogModel=new LayinChatLogModel();
                Diskuser diskuser=diskuserMapper.selectByPrimaryKey(groupmessage.getFormid());
                layinChatLogModel.setId(groupmessage.getFormid());
                layinChatLogModel.setAvatar(diskuser.getImgpath());
                layinChatLogModel.setUsername(diskuser.getName());
                layinChatLogModel.setTimestamp(groupmessage.getTime());
                layinChatLogModel.setContent(groupmessage.getMessage());
                layinChatLogModels.add(layinChatLogModel);
            }
        }

        return layinChatLogModels;
    }

    @Override
    public List<LayimUserModel> searchUserbyname(String name) {
        List<LayimUserModel> list=new ArrayList<>();
        List<Diskuser> diskusers=diskuserMapper.selectusersbyname(name);
        for(Diskuser diskuser:diskusers)
        {
            LayimUserModel layimUserModel=new LayimUserModel();
            layimUserModel.setAvatar(diskuser.getImgpath());
            layimUserModel.setSign(diskuser.getSign());
            layimUserModel.setId(diskuser.getUserid());
            if(diskuser.getStatue()==1)//登录状态
                layimUserModel.setStatus("online");
            layimUserModel.setUsername(diskuser.getName());
            list.add(layimUserModel);
        }
        return list;
    }

    @Override
    public List<LayimGroupModel> searchGroupbyname(String name) {
        List<LayimGroupModel> list=new ArrayList<>();
        List<Resgroup> resgroupList=resgroupMapper.selectgroupsbyname(name);
        for(Resgroup resgroup:resgroupList)
        {
            LayimGroupModel layimGroupModel=new LayimGroupModel();
            layimGroupModel.setId(resgroup.getGroupid());
            layimGroupModel.setAvatar(resgroup.getImgpath());
            layimGroupModel.setGroupname(resgroup.getName());
            list.add(layimGroupModel);
        }
        return list;
    }

    @Override
    public List<LayimMsgboxModel> getMsgbox(int userid) {
        List<LayimMsgboxModel> layimMsgboxModels=new ArrayList<>();
        List<Addapply> list=addapplyMapper.findAllAddapplybyUserid(userid);
        for(Addapply addapply:list)
        {
            LayimMsgboxModel layimMsgboxModel=new LayimMsgboxModel();

            layimMsgboxModel.setRemark(addapply.getMessage());
            layimMsgboxModel.setTime(addapply.getTime());
            layimMsgboxModel.setId(addapply.getId());
            layimMsgboxModel.setType(addapply.getStatue());
            layimMsgboxModel.setFrom(addapply.getFormid());
            LayimUserModel layimUserModel=new LayimUserModel();
            Diskuser diskuser=diskuserMapper.selectByPrimaryKey(addapply.getFormid());
            layimUserModel.setAvatar(diskuser.getImgpath());
            layimUserModel.setSign(diskuser.getSign());
            layimUserModel.setId(diskuser.getUserid());
            layimUserModel.setUsername(diskuser.getName());
            layimMsgboxModel.setRead(1);
            layimMsgboxModel.setUser(layimUserModel);
            if(addapply.getGroupid()==null)
            {
                layimMsgboxModel.setUid(userid);
                layimMsgboxModel.setContent("申请添加你为好友");
            }else{
                layimMsgboxModel.setUid(addapply.getGroupid());
                Resgroup resgroup=resgroupMapper.selectByPrimaryKey(addapply.getGroupid());
                layimMsgboxModel.setContent("申请加入"+resgroup.getName());
            }
            layimMsgboxModels.add(layimMsgboxModel);
        }
        return layimMsgboxModels;
    }
}

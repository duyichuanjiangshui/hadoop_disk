package com.liangrui.hadoop_disk.bean.model.layim;

import java.util.List;

/*
*    mine: {
        "username": "LayIM体验者" //我的昵称
        ,"id": "100000123" //我的ID
        ,"status": "online" //在线状态 online：在线、hide：隐身
        ,"remark": "在深邃的编码世界，做一枚轻盈的纸飞机" //我的签名
        ,"avatar": "a.jpg" //我的头像
      }
      ,friend: []
      ,group: []
* */

public class InitDataModel {
    private LayimUserModel mine;
    private List<LayimFriendSMoldel> friend;
    private List<LayimGroupModel> group;

    public LayimUserModel getMine() {
        return mine;
    }

    public void setMine(LayimUserModel mine) {
        this.mine = mine;
    }

    public List<LayimFriendSMoldel> getFriend() {
        return friend;
    }

    public void setFriend(List<LayimFriendSMoldel> friend) {
        this.friend = friend;
    }

    public List<LayimGroupModel> getGroup() {
        return group;
    }

    public void setGroup(List<LayimGroupModel> group) {
        this.group = group;
    }
}


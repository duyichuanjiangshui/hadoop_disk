package com.liangrui.hadoop_disk.service;

import com.liangrui.hadoop_disk.bean.model.layim.*;

import java.util.List;
import java.util.Map;

public interface LayimService {
    InitDataModel initlayim(int userid);
    List<LayimChatModel> getNoReadMessage(int userid);
    List<LayinChatLogModel> getOldMessage(int pageNo, int pageSize, int toid, String type, int userid);
    List<LayimUserModel> searchUserbyname(String name);
    List<LayimGroupModel> searchGroupbyname(String name);
    List<LayimMsgboxModel> getMsgbox(int userid);
}

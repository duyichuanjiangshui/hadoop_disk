package com.liangrui.hadoop_disk.bean.model.layim;

import java.util.Date;

public class LayimChatModel {

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
    //消息的来源id
    private Integer id;
    //消息接收方的id
    private Integer toId;
    private String username;
    //签名
    private String sign;
    //头像
    private String avatar;
    //消息内容
    private String content;
    //消息的来源类型
    private String type;
    //是否是本人
    private Boolean mine = false;
    //发送消息的时间
    private Date timestamp = new Date();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getToId() {
        return toId;
    }

    public void setToId(Integer toId) {
        this.toId = toId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getMine() {
        return mine;
    }

    public void setMine(Boolean mine) {
        this.mine = mine;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}

package com.liangrui.hadoop_disk.bean.model.layim;

public class LayinChatLogModel {
    /*
    *  username: '纸飞机'
      ,id: 100000
      ,avatar: 'http://tva3.sinaimg.cn/crop.0.0.512.512.180/8693225ajw8f2rt20ptykj20e80e8weu.jpg'
      ,timestamp: 1480897882000
      ,content: 'face[抱抱] face[心] 你好啊小美女'
    * */
    private String username;
    private int id;
    private String avatar;
    private String timestamp;
    private String content;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

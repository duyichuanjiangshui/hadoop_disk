package com.liangrui.hadoop_disk.bean.model.layim;

public class LayimMsgboxModel {
    /**
     * "id": 76,
     *             "content": "申请添加你为好友",
     *             "uid": 168,
     *             "from": 166488,
     *             "from_group": 0,
     *             "type": 1,
     *             "remark": "有问题要问",
     *             "href": null,
     *             "read": 1,
     *             "time": "刚刚",
     *             "user": {
     *                 "id": 166488,
     *                 "avatar": "http://q.qlogo.cn/qqapp/101235792/B704597964F9BD0DB648292D1B09F7E8/100",
     *                 "username": "李彦宏",
     *                 "sign": null
     *             }
     */
    private int id;
    private String content;
    private int uid;
    private int from;
    private int from_group;
    private int type;
    private String remark;
    private String herf;
    private int read;
    private String time;
    private LayimUserModel user;

 public int getId() {
  return id;
 }

 public void setId(int id) {
  this.id = id;
 }

 public String getContent() {
  return content;
 }

 public void setContent(String content) {
  this.content = content;
 }

 public int getUid() {
  return uid;
 }

 public void setUid(int uid) {
  this.uid = uid;
 }

 public int getFrom() {
  return from;
 }

 public void setFrom(int from) {
  this.from = from;
 }

 public int getFrom_group() {
  return from_group;
 }

 public void setFrom_group(int from_group) {
  this.from_group = from_group;
 }

 public int getType() {
  return type;
 }

 public void setType(int type) {
  this.type = type;
 }

 public String getRemark() {
  return remark;
 }

 public void setRemark(String remark) {
  this.remark = remark;
 }

 public String getHerf() {
  return herf;
 }

 public void setHerf(String herf) {
  this.herf = herf;
 }

 public int getRead() {
  return read;
 }

 public void setRead(int read) {
  this.read = read;
 }

 public String getTime() {
  return time;
 }

 public void setTime(String time) {
  this.time = time;
 }

 public LayimUserModel getUser() {
  return user;
 }

 public void setUser(LayimUserModel user) {
  this.user = user;
 }
}

package com.liangrui.hadoop_disk.mapper;


import com.liangrui.hadoop_disk.bean.entity.Folder;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
@Mapper
public interface FolderMapper {
    @Delete({
            "delete from folder",
            "where folderId = #{folderid,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String folderid);

    @Insert({
            "insert into folder (folderId, fatherFolderId, ",
            "groupId, name, updateTime, ",
            "userId, createTime, ",
            "shareType, isDelete, ",
            "deletetime)",
            "values (#{folderid,jdbcType=VARCHAR}, #{fatherfolderid,jdbcType=VARCHAR}, ",
            "#{groupid,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, #{updatetime,jdbcType=VARCHAR}, ",
            "#{userid,jdbcType=INTEGER}, #{createtime,jdbcType=VARCHAR}, ",
            "#{sharetype,jdbcType=INTEGER}, #{isdelete,jdbcType=INTEGER}, ",
            "#{deletetime,jdbcType=VARCHAR})"
    })
    int insert(Folder record);



    @Select({
            "select",
            "folderId, fatherFolderId, groupId, name, updateTime, userId, createTime, shareType, ",
            "isDelete, deletetime",
            "from folder",
            "where folderId = #{folderid,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="folderId", property="folderid", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="fatherFolderId", property="fatherfolderid", jdbcType=JdbcType.VARCHAR),
            @Result(column="groupId", property="groupid", jdbcType=JdbcType.INTEGER),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="updateTime", property="updatetime", jdbcType=JdbcType.VARCHAR),
            @Result(column="userId", property="userid", jdbcType=JdbcType.INTEGER),
            @Result(column="createTime", property="createtime", jdbcType=JdbcType.VARCHAR),
            @Result(column="shareType", property="sharetype", jdbcType=JdbcType.INTEGER),
            @Result(column="isDelete", property="isdelete", jdbcType=JdbcType.INTEGER),
            @Result(column="deletetime", property="deletetime", jdbcType=JdbcType.VARCHAR)
    })
    Folder selectByPrimaryKey(String folderid);

    @Update({
            "update folder",
            "set fatherFolderId = #{fatherfolderid,jdbcType=VARCHAR},",
            "groupId = #{groupid,jdbcType=INTEGER},",
            "name = #{name,jdbcType=VARCHAR},",
            "updateTime = #{updatetime,jdbcType=VARCHAR},",
            "userId = #{userid,jdbcType=INTEGER},",
            "createTime = #{createtime,jdbcType=VARCHAR},",
            "shareType = #{sharetype,jdbcType=INTEGER},",
            "isDelete = #{isdelete,jdbcType=INTEGER},",
            "deletetime = #{deletetime,jdbcType=VARCHAR}",
            "where folderId = #{folderid,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(Folder record);

    @Select({
            "select * from folder "+
            "where fatherFolderId = #{fatherFolderid} and isDelete=0 "
    })
    List<Folder> selectByFatherFolder(String fatherFolderid);
    @Select({
            "select * from folder "+
                    "where sharetype=1 and fatherFolderId = #{fatherFolderid} and isDelete=0 "
    })
    List<Folder> selectPublicByFatherFolder(String fatherFolderid);
    @Select({
            "select * from folder "+
            "where  userId=#{userid} and isDelete=0 and groupId is null"
    })
    List<Folder> selectByUserid(int userid);

    @Select("SELECT * from folder where  name= #{name} and fatherFolderId= #{fatherFolderid} and isDelete=0 and groupId is null limit 1")
    Folder selectByname(@Param("name") String name, @Param("fatherFolderid") String fatherFolderid);
    @Select("SELECT * from folder where userId= #{userId} and isDelete=0 and groupId is null and `name` like '%${name}%'")
    List<Folder> selectByLikeName(@Param("userId") int userId,@Param("name") String name);
    @Select("SELECT * from folder where userId= #{userId} and isDelete=1 and groupId is null")
    List<Folder> selectrecycle(int userId);
    @Select("SELECT * from folder where groupId=#{groupid} and `name` like '%${name}%'")
    List<Folder> selectGroupfileByLikeName(int groupid,@Param("name") String name);
    @Select("SELECT * from folder where groupId=#{groupid} and userId= #{userid}")
    List<Folder> selectGroupfileByuserid(int groupid,int userid);
    @Select({
            "select * from folder "+
                    "where   groupId =#{groupid}"
    })
    List<Folder> selectByGroupid(int groupid);
    @Select("SELECT * from folder where sharetype=1 and isDelete=0 and groupId is null and `name` like '%${name}%'")
    List<Folder> selectAllPublicByLikeName(@Param("name") String name);

}
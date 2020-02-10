package com.liangrui.hadoop_disk.mapper;


import com.liangrui.hadoop_disk.bean.entity.Folder;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
@Mapper
public interface FolderMapper {
    @Delete({
        "delete from folder",
        "where folderId = #{folderid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer folderid);

    @Insert({
        "insert into folder (folderId, fatherFolderId, ",
        "name, updateTime, ",
        "userId, createTime, ",
        "shareType, isDelete)",
        "values (#{folderid,jdbcType=INTEGER}, #{fatherfolderid,jdbcType=INTEGER}, ",
        "#{name,jdbcType=VARCHAR}, #{updatetime,jdbcType=VARCHAR}, ",
        "#{userid,jdbcType=INTEGER}, #{createtime,jdbcType=VARCHAR}, ",
        "#{sharetype,jdbcType=INTEGER}, #{isdelete,jdbcType=INTEGER})"
    })
    int insert(Folder record);


    @Select({
        "select",
        "folderId, fatherFolderId, name, updateTime, userId, createTime, shareType, isDelete",
        "from folder",
        "where folderId = #{folderid,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="folderId", property="folderid", jdbcType= JdbcType.INTEGER, id=true),
        @Result(column="fatherFolderId", property="fatherfolderid", jdbcType= JdbcType.INTEGER),
        @Result(column="name", property="name", jdbcType= JdbcType.VARCHAR),
        @Result(column="updateTime", property="updatetime", jdbcType= JdbcType.VARCHAR),
        @Result(column="userId", property="userid", jdbcType= JdbcType.INTEGER),
        @Result(column="createTime", property="createtime", jdbcType= JdbcType.VARCHAR),
        @Result(column="shareType", property="sharetype", jdbcType= JdbcType.INTEGER),
        @Result(column="isDelete", property="isdelete", jdbcType= JdbcType.INTEGER)
    })
    Folder selectByPrimaryKey(Integer folderid);
    @Select({
            "select",
            "folderId, fatherFolderId, name, updateTime, userId, createTime, shareType, isDelete",
            "from folder",
            "where fatherFolderId = #{fatherFolderid,jdbcType=INTEGER} and userId=#{userid} and isDelete=0 "
    })
    @Results({
            @Result(column="folderId", property="folderid", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="fatherFolderId", property="fatherfolderid", jdbcType= JdbcType.INTEGER),
            @Result(column="name", property="name", jdbcType= JdbcType.VARCHAR),
            @Result(column="updateTime", property="updatetime", jdbcType= JdbcType.VARCHAR),
            @Result(column="userId", property="userid", jdbcType= JdbcType.INTEGER),
            @Result(column="createTime", property="createtime", jdbcType= JdbcType.VARCHAR),
            @Result(column="shareType", property="sharetype", jdbcType= JdbcType.INTEGER),
            @Result(column="isDelete", property="isdelete", jdbcType= JdbcType.INTEGER)
    })
    List<Folder> selectByFatherFolder( Integer fatherFolderid,int userid);

    @Select({
            "select",
            "folderId, fatherFolderId, name, updateTime, userId, createTime, shareType, isDelete",
            "from folder",
            "where  userId=#{userid} and isDelete=0 "
    })
    @Results({
            @Result(column="folderId", property="folderid", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="fatherFolderId", property="fatherfolderid", jdbcType= JdbcType.INTEGER),
            @Result(column="name", property="name", jdbcType= JdbcType.VARCHAR),
            @Result(column="updateTime", property="updatetime", jdbcType= JdbcType.VARCHAR),
            @Result(column="userId", property="userid", jdbcType= JdbcType.INTEGER),
            @Result(column="createTime", property="createtime", jdbcType= JdbcType.VARCHAR),
            @Result(column="shareType", property="sharetype", jdbcType= JdbcType.INTEGER),
            @Result(column="isDelete", property="isdelete", jdbcType= JdbcType.INTEGER)
    })
    List<Folder> selectByUserid(int userid);

    @Update({
        "update folder",
        "set fatherFolderId = #{fatherfolderid,jdbcType=INTEGER},",
          "name = #{name,jdbcType=VARCHAR},",
          "updateTime = #{updatetime,jdbcType=VARCHAR},",
          "userId = #{userid,jdbcType=INTEGER},",
          "createTime = #{createtime,jdbcType=VARCHAR},",
          "shareType = #{sharetype,jdbcType=INTEGER},",
          "isDelete = #{isdelete,jdbcType=INTEGER}",
        "where folderId = #{folderid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Folder record);
    @Select("SELECT * from folder where userId = #{userId} and name= #{name} and fatherFolderId= #{fatherFolderid} and isDelete=0 limit 1")
    Folder selectByname(@Param("userId") Integer userId,@Param("name") String name,@Param("fatherFolderid") int fatherFolderid);
}
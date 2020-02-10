package com.liangrui.hadoop_disk.mapper;

import com.liangrui.hadoop_disk.bean.entity.Fileindex;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
@Mapper
public interface FileindexMapper {
    @Delete({
        "delete from fileindex",
        "where fileId = #{fileid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer fileid);

    @Insert({
        "insert into fileindex (fileId, fatherFolder, ",
        "uploadLocationId, size, ",
        "name, updateTime, ",
        "uploadTime, userId, ",
        "shareType, SaveNum, ",
        "fileType, isDelete)",
        "values (#{fileid,jdbcType=INTEGER}, #{fatherfolder,jdbcType=INTEGER}, ",
        "#{uploadlocationid,jdbcType=INTEGER}, #{size,jdbcType=REAL}, ",
        "#{name,jdbcType=VARCHAR}, #{updatetime,jdbcType=VARCHAR}, ",
        "#{uploadtime,jdbcType=VARCHAR}, #{userid,jdbcType=INTEGER}, ",
        "#{sharetype,jdbcType=INTEGER}, #{savenum,jdbcType=INTEGER}, ",
        "#{filetype,jdbcType=VARCHAR}, #{isdelete,jdbcType=INTEGER})"
    })
    int insert(Fileindex record);


    @Select({
        "select",
        "fileId, fatherFolder, uploadLocationId, size, name, updateTime, uploadTime, ",
        "userId, shareType, SaveNum, fileType, isDelete",
        "from fileindex",
        "where fileId = #{fileid,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="fileId", property="fileid", jdbcType= JdbcType.INTEGER, id=true),
        @Result(column="fatherFolder", property="fatherfolder", jdbcType= JdbcType.INTEGER),
        @Result(column="uploadLocationId", property="uploadlocationid", jdbcType= JdbcType.INTEGER),
        @Result(column="size", property="size", jdbcType= JdbcType.REAL),
        @Result(column="name", property="name", jdbcType= JdbcType.VARCHAR),
        @Result(column="updateTime", property="updatetime", jdbcType= JdbcType.VARCHAR),
        @Result(column="uploadTime", property="uploadtime", jdbcType= JdbcType.VARCHAR),
        @Result(column="userId", property="userid", jdbcType= JdbcType.INTEGER),
        @Result(column="shareType", property="sharetype", jdbcType= JdbcType.INTEGER),
        @Result(column="SaveNum", property="savenum", jdbcType= JdbcType.INTEGER),
        @Result(column="fileType", property="filetype", jdbcType= JdbcType.VARCHAR),
        @Result(column="isDelete", property="isdelete", jdbcType= JdbcType.INTEGER)
    })
    Fileindex selectByPrimaryKey(Integer fileid);

    @Select({
            "select",
            "fileId, fatherFolder, uploadLocationId, size, name, updateTime, uploadTime, ",
            "userId, shareType, SaveNum, fileType, isDelete",
            "from fileindex",
            "where fatherFolder = #{fatherFolderid,jdbcType=INTEGER} and userId = #{userid} and isDelete=0"
    })
    @Results({
            @Result(column="fileId", property="fileid", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="fatherFolder", property="fatherfolder", jdbcType= JdbcType.INTEGER),
            @Result(column="uploadLocationId", property="uploadlocationid", jdbcType= JdbcType.INTEGER),
            @Result(column="size", property="size", jdbcType= JdbcType.REAL),
            @Result(column="name", property="name", jdbcType= JdbcType.VARCHAR),
            @Result(column="updateTime", property="updatetime", jdbcType= JdbcType.VARCHAR),
            @Result(column="uploadTime", property="uploadtime", jdbcType= JdbcType.VARCHAR),
            @Result(column="userId", property="userid", jdbcType= JdbcType.INTEGER),
            @Result(column="shareType", property="sharetype", jdbcType= JdbcType.INTEGER),
            @Result(column="SaveNum", property="savenum", jdbcType= JdbcType.INTEGER),
            @Result(column="fileType", property="filetype", jdbcType= JdbcType.VARCHAR),
            @Result(column="isDelete", property="isdelete", jdbcType= JdbcType.INTEGER)
    })
    List<Fileindex> selectByFatherFolder(Integer fatherFolderid ,int userid);
    @Update({
        "update fileindex",
        "set fatherFolder = #{fatherfolder,jdbcType=INTEGER},",
          "uploadLocationId = #{uploadlocationid,jdbcType=INTEGER},",
          "size = #{size,jdbcType=REAL},",
          "name = #{name,jdbcType=VARCHAR},",
          "updateTime = #{updatetime,jdbcType=VARCHAR},",
          "uploadTime = #{uploadtime,jdbcType=VARCHAR},",
          "userId = #{userid,jdbcType=INTEGER},",
          "shareType = #{sharetype,jdbcType=INTEGER},",
          "SaveNum = #{savenum,jdbcType=INTEGER},",
          "fileType = #{filetype,jdbcType=VARCHAR},",
          "isDelete = #{isdelete,jdbcType=INTEGER}",
        "where fileId = #{fileid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Fileindex record);
    @Select("SELECT * from fileindex where userId = #{userId} and name= #{name} and fatherFolder= #{fatherFolderid} limit 1")
    Fileindex selectByname(@Param("userId") Integer userId,@Param("name") String name,@Param("fatherFolderid") int fatherFolderid);
}
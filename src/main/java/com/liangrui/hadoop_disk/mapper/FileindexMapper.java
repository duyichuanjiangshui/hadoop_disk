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
            "insert into fileindex (fileId, fatherFolderid, ",
            "groupId, uploadLocationId, ",
            "size, name, updateTime, ",
            "uploadTime, userId, ",
            "deleteTime, shareType, ",
            "SaveNum, fileType, ",
            "sortype, isDelete)",
            "values (#{fileid,jdbcType=INTEGER}, #{fatherfolderid,jdbcType=VARCHAR}, ",
            "#{groupid,jdbcType=INTEGER}, #{uploadlocationid,jdbcType=INTEGER}, ",
            "#{size,jdbcType=REAL}, #{name,jdbcType=VARCHAR}, #{updatetime,jdbcType=VARCHAR}, ",
            "#{uploadtime,jdbcType=VARCHAR}, #{userid,jdbcType=INTEGER}, ",
            "#{deletetime,jdbcType=VARCHAR}, #{sharetype,jdbcType=INTEGER}, ",
            "#{savenum,jdbcType=INTEGER}, #{filetype,jdbcType=VARCHAR}, ",
            "#{sortype,jdbcType=INTEGER}, #{isdelete,jdbcType=INTEGER})"
    })
    int insert(Fileindex record);


    @Select({
            "select",
            "fileId, fatherFolderid, groupId, uploadLocationId, size, name, updateTime, uploadTime, ",
            "userId, deleteTime, shareType, SaveNum, fileType, sortype, isDelete",
            "from fileindex",
            "where fileId = #{fileid,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="fileId", property="fileid", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="fatherFolderid", property="fatherfolderid", jdbcType=JdbcType.VARCHAR),
            @Result(column="groupId", property="groupid", jdbcType=JdbcType.INTEGER),
            @Result(column="uploadLocationId", property="uploadlocationid", jdbcType=JdbcType.INTEGER),
            @Result(column="size", property="size", jdbcType=JdbcType.REAL),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="updateTime", property="updatetime", jdbcType=JdbcType.VARCHAR),
            @Result(column="uploadTime", property="uploadtime", jdbcType=JdbcType.VARCHAR),
            @Result(column="userId", property="userid", jdbcType=JdbcType.INTEGER),
            @Result(column="deleteTime", property="deletetime", jdbcType=JdbcType.VARCHAR),
            @Result(column="shareType", property="sharetype", jdbcType=JdbcType.INTEGER),
            @Result(column="SaveNum", property="savenum", jdbcType=JdbcType.INTEGER),
            @Result(column="fileType", property="filetype", jdbcType=JdbcType.VARCHAR),
            @Result(column="sortype", property="sortype", jdbcType=JdbcType.INTEGER),
            @Result(column="isDelete", property="isdelete", jdbcType=JdbcType.INTEGER)
    })
    Fileindex selectByPrimaryKey(Integer fileid);

    @Update({
            "update fileindex",
            "set fatherFolderid = #{fatherfolderid,jdbcType=VARCHAR},",
            "groupId = #{groupid,jdbcType=INTEGER},",
            "uploadLocationId = #{uploadlocationid,jdbcType=INTEGER},",
            "size = #{size,jdbcType=REAL},",
            "name = #{name,jdbcType=VARCHAR},",
            "updateTime = #{updatetime,jdbcType=VARCHAR},",
            "uploadTime = #{uploadtime,jdbcType=VARCHAR},",
            "userId = #{userid,jdbcType=INTEGER},",
            "deleteTime = #{deletetime,jdbcType=VARCHAR},",
            "shareType = #{sharetype,jdbcType=INTEGER},",
            "SaveNum = #{savenum,jdbcType=INTEGER},",
            "fileType = #{filetype,jdbcType=VARCHAR},",
            "sortype = #{sortype,jdbcType=INTEGER},",
            "isDelete = #{isdelete,jdbcType=INTEGER}",
            "where fileId = #{fileid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Fileindex record);

    
    @Select({
            "select  * from fileindex "+
            "where fatherFolderid = #{fatherFolderid}  and isDelete=0"
    })
    List<Fileindex> selectByFatherFolder(String fatherFolderid);

    @Select("SELECT * from fileindex where   name= #{name} and fatherFolderid= #{fatherFolderid} and groupId is null and isDelete=0 limit 1")
    Fileindex selectByname( @Param("name") String name, @Param("fatherFolderid") String fatherFolderid);
    @Select("SELECT * from fileindex WHERE userId= #{userId} and isDelete=0 and groupId is null and  `name` like '%${name}%'")
    List<Fileindex> selectByLikeName(@Param("userId") int userId,@Param("name") String name);
    @Select("SELECT * from fileindex where userId =#{userId} and isDelete=0 and groupId is null and sortype=#{sortype}")
    List<Fileindex> selectbyFiletype(int userId,int sortype);
    @Select("SELECT * from fileindex WHERE userId= #{userId} and isDelete=0 and sortype=#{sortype} and groupId is null and  `name` like '%${name}%'")
    List<Fileindex> selectAlonefileByLikeName(@Param("userId") int userId,int sortype,@Param("name") String name);
    @Select("SELECT * from fileindex WHERE userId= #{userId} and isDelete=1")
    List<Fileindex> selectrecycle(int userId);
    @Select("SELECT * from fileindex WHERE   groupId=#{groupid} and  `name` like '%${name}%'")
    List<Fileindex> selectGroupFileByLikeName( int groupid,@Param("name") String name);
    @Select("SELECT * from fileindex WHERE   groupId=#{groupid} and  userId= #{userid}")
    List<Fileindex> selectGroupfileByuserid( int groupid,int userid);

}
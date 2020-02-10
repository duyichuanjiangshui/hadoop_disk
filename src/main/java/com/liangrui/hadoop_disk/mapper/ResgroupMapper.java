package com.liangrui.hadoop_disk.mapper;

import com.liangrui.hadoop_disk.bean.entity.Resgroup;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
@Mapper
public interface ResgroupMapper {
    @Delete({
        "delete from resgroup",
        "where groupId = #{groupid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer groupid);

    @Insert({
        "insert into resgroup (groupId, name, ",
        "createTime, imgPath, ",
        "descripe, userId)",
        "values (#{groupid,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{createtime,jdbcType=VARCHAR}, #{imgpath,jdbcType=VARCHAR}, ",
        "#{descripe,jdbcType=VARCHAR}, #{userid,jdbcType=INTEGER})"
    })
    int insert(Resgroup record);



    @Select({
        "select",
        "groupId, name, createTime, imgPath, descripe, userId",
        "from resgroup",
        "where groupId = #{groupid,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="groupId", property="groupid", jdbcType= JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType= JdbcType.VARCHAR),
        @Result(column="createTime", property="createtime", jdbcType= JdbcType.VARCHAR),
        @Result(column="imgPath", property="imgpath", jdbcType= JdbcType.VARCHAR),
        @Result(column="descripe", property="descripe", jdbcType= JdbcType.VARCHAR),
        @Result(column="userId", property="userid", jdbcType= JdbcType.INTEGER)
    })
    Resgroup selectByPrimaryKey(Integer groupid);



    @Update({
        "update resgroup",
        "set name = #{name,jdbcType=VARCHAR},",
          "createTime = #{createtime,jdbcType=VARCHAR},",
          "imgPath = #{imgpath,jdbcType=VARCHAR},",
          "descripe = #{descripe,jdbcType=VARCHAR},",
          "userId = #{userid,jdbcType=INTEGER}",
        "where groupId = #{groupid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Resgroup record);
}
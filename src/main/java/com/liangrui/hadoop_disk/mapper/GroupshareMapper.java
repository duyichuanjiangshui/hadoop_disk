package com.liangrui.hadoop_disk.mapper;

import com.liangrui.hadoop_disk.bean.entity.Groupshare;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
@Mapper
public interface GroupshareMapper {
    @Delete({
        "delete from groupshare",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into groupshare (id, groupId, ",
        "userId, startTime, ",
        "resourceType, resourceTypeId)",
        "values (#{id,jdbcType=INTEGER}, #{groupid,jdbcType=INTEGER}, ",
        "#{userid,jdbcType=INTEGER}, #{starttime,jdbcType=VARCHAR}, ",
        "#{resourcetype,jdbcType=INTEGER}, #{resourcetypeid,jdbcType=INTEGER})"
    })
    int insert(Groupshare record);



    @Select({
        "select",
        "id, groupId, userId, startTime, resourceType, resourceTypeId",
        "from groupshare",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
        @Result(column="groupId", property="groupid", jdbcType= JdbcType.INTEGER),
        @Result(column="userId", property="userid", jdbcType= JdbcType.INTEGER),
        @Result(column="startTime", property="starttime", jdbcType= JdbcType.VARCHAR),
        @Result(column="resourceType", property="resourcetype", jdbcType= JdbcType.INTEGER),
        @Result(column="resourceTypeId", property="resourcetypeid", jdbcType= JdbcType.INTEGER)
    })
    Groupshare selectByPrimaryKey(Integer id);



    @Update({
        "update groupshare",
        "set groupId = #{groupid,jdbcType=INTEGER},",
          "userId = #{userid,jdbcType=INTEGER},",
          "startTime = #{starttime,jdbcType=VARCHAR},",
          "resourceType = #{resourcetype,jdbcType=INTEGER},",
          "resourceTypeId = #{resourcetypeid,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Groupshare record);
}
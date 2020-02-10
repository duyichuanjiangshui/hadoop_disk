package com.liangrui.hadoop_disk.mapper;


import com.liangrui.hadoop_disk.bean.entity.Usermessage;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
@Mapper
public interface UsermessageMapper {
    @Delete({
        "delete from usermessage",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into usermessage (id, toUserId, ",
        "fromUserId, time, ",
        "message)",
        "values (#{id,jdbcType=INTEGER}, #{touserid,jdbcType=INTEGER}, ",
        "#{fromuserid,jdbcType=INTEGER}, #{time,jdbcType=VARCHAR}, ",
        "#{message,jdbcType=LONGVARCHAR})"
    })
    int insert(Usermessage record);


    @Select({
        "select",
        "id, toUserId, fromUserId, time, message",
        "from usermessage",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
        @Result(column="toUserId", property="touserid", jdbcType= JdbcType.INTEGER),
        @Result(column="fromUserId", property="fromuserid", jdbcType= JdbcType.INTEGER),
        @Result(column="time", property="time", jdbcType= JdbcType.VARCHAR),
        @Result(column="message", property="message", jdbcType= JdbcType.LONGVARCHAR)
    })
    Usermessage selectByPrimaryKey(Integer id);

    @Update({
        "update usermessage",
        "set toUserId = #{touserid,jdbcType=INTEGER},",
          "fromUserId = #{fromuserid,jdbcType=INTEGER},",
          "time = #{time,jdbcType=VARCHAR},",
          "message = #{message,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(Usermessage record);

    @Update({
        "update usermessage",
        "set toUserId = #{touserid,jdbcType=INTEGER},",
          "fromUserId = #{fromuserid,jdbcType=INTEGER},",
          "time = #{time,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Usermessage record);
}
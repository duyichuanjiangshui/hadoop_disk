package com.liangrui.hadoop_disk.mapper;


import com.liangrui.hadoop_disk.bean.entity.Friend;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
@Mapper
public interface FriendMapper {
    @Delete({
        "delete from friend",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into friend (id, masterId, ",
        "friendId, establishTime, ",
        "comment)",
        "values (#{id,jdbcType=INTEGER}, #{masterid,jdbcType=INTEGER}, ",
        "#{friendid,jdbcType=INTEGER}, #{establishtime,jdbcType=VARCHAR}, ",
        "#{comment,jdbcType=VARCHAR})"
    })
    int insert(Friend record);



    @Select({
        "select",
        "id, masterId, friendId, establishTime, comment",
        "from friend",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
        @Result(column="masterId", property="masterid", jdbcType= JdbcType.INTEGER),
        @Result(column="friendId", property="friendid", jdbcType= JdbcType.INTEGER),
        @Result(column="establishTime", property="establishtime", jdbcType= JdbcType.VARCHAR),
        @Result(column="comment", property="comment", jdbcType= JdbcType.VARCHAR)
    })
    Friend selectByPrimaryKey(Integer id);



    @Update({
        "update friend",
        "set masterId = #{masterid,jdbcType=INTEGER},",
          "friendId = #{friendid,jdbcType=INTEGER},",
          "establishTime = #{establishtime,jdbcType=VARCHAR},",
          "comment = #{comment,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Friend record);
}
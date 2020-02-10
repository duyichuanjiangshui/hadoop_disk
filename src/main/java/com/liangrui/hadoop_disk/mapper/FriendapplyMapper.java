package com.liangrui.hadoop_disk.mapper;

import com.liangrui.hadoop_disk.bean.entity.Friendapply;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
@Mapper
public interface FriendapplyMapper {
    @Delete({
        "delete from friendapply",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into friendapply (id, masterId, ",
        "friendId, applyTime, ",
        "statue, message)",
        "values (#{id,jdbcType=INTEGER}, #{masterid,jdbcType=INTEGER}, ",
        "#{friendid,jdbcType=INTEGER}, #{applytime,jdbcType=VARCHAR}, ",
        "#{statue,jdbcType=INTEGER}, #{message,jdbcType=VARCHAR})"
    })
    int insert(Friendapply record);



    @Select({
        "select",
        "id, masterId, friendId, applyTime, statue, message",
        "from friendapply",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
        @Result(column="masterId", property="masterid", jdbcType= JdbcType.INTEGER),
        @Result(column="friendId", property="friendid", jdbcType= JdbcType.INTEGER),
        @Result(column="applyTime", property="applytime", jdbcType= JdbcType.VARCHAR),
        @Result(column="statue", property="statue", jdbcType= JdbcType.INTEGER),
        @Result(column="message", property="message", jdbcType= JdbcType.VARCHAR)
    })
    Friendapply selectByPrimaryKey(Integer id);



    @Update({
        "update friendapply",
        "set masterId = #{masterid,jdbcType=INTEGER},",
          "friendId = #{friendid,jdbcType=INTEGER},",
          "applyTime = #{applytime,jdbcType=VARCHAR},",
          "statue = #{statue,jdbcType=INTEGER},",
          "message = #{message,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Friendapply record);
}
package com.liangrui.hadoop_disk.mapper;


import com.liangrui.hadoop_disk.bean.entity.Groupmessage;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface GroupmessageMapper {
    @Delete({
        "delete from groupmessage",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into groupmessage (id, formid, ",
        "message, time, groupid)",
        "values (#{id,jdbcType=INTEGER}, #{formid,jdbcType=INTEGER}, ",
        "#{message,jdbcType=VARCHAR}, #{time,jdbcType=VARCHAR}, #{groupid,jdbcType=INTEGER})"
    })
    int insert(Groupmessage record);



    @Select({
        "select",
        "id, formid, message, time, groupid",
        "from groupmessage",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
        @Result(column="formid", property="formid", jdbcType= JdbcType.INTEGER),
        @Result(column="message", property="message", jdbcType= JdbcType.VARCHAR),
        @Result(column="time", property="time", jdbcType= JdbcType.VARCHAR),
        @Result(column="groupid", property="groupid", jdbcType= JdbcType.INTEGER)
    })
    Groupmessage selectByPrimaryKey(Integer id);


    @Update({
        "update groupmessage",
        "set formid = #{formid,jdbcType=INTEGER},",
          "message = #{message,jdbcType=VARCHAR},",
          "time = #{time,jdbcType=VARCHAR},",
          "groupid = #{groupid,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Groupmessage record);
    @Select("select * from groupmessage where  groupid=#{toid}  " +
            "ORDER BY time DESC LIMIT #{index} , #{limit}")
    List<Groupmessage> findchatlog(@Param("index") Integer index, @Param("limit") Integer limit,
                                    @Param("toid") int toid);

    @Delete("delete from groupmessage where groupid=#{groupid}")
    int deleteGroupmessage(int groupid);
}
package com.liangrui.hadoop_disk.mapper;

import com.liangrui.hadoop_disk.bean.entity.Groupnumber;
import java.util.List;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
@Mapper
public interface GroupnumberMapper {
    @Delete({
        "delete from groupnumber",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into groupnumber (id, groupId, ",
        "userId, joinTime)",
        "values (#{id,jdbcType=INTEGER}, #{groupid,jdbcType=INTEGER}, ",
        "#{userid,jdbcType=INTEGER}, #{jointime,jdbcType=VARCHAR})"
    })
    int insert(Groupnumber record);



    @Select({
        "select",
        "id, groupId, userId, joinTime",
        "from groupnumber",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
        @Result(column="groupId", property="groupid", jdbcType= JdbcType.INTEGER),
        @Result(column="userId", property="userid", jdbcType= JdbcType.INTEGER),
        @Result(column="joinTime", property="jointime", jdbcType= JdbcType.VARCHAR)
    })
    Groupnumber selectByPrimaryKey(Integer id);



    @Update({
        "update groupnumber",
        "set groupId = #{groupid,jdbcType=INTEGER},",
          "userId = #{userid,jdbcType=INTEGER},",
          "joinTime = #{jointime,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Groupnumber record);
    @Select("select * from groupnumber where userId = #{userid}")
    List<Groupnumber> findAllGroupnumberbyuserid(int userid);
    @Select("select * from groupnumber where groupId = #{groupid}")
    List<Groupnumber> findAllGroupnumberbyGroupid(int groupid);

    @Select("select id from groupnumber where groupId = #{groupid} and userId = #{userid}")
    Integer findbygrouidanduserid(int groupid,int userid);

    @Delete("delete from groupnumber where groupId = #{groupid} and userId = #{userid}")
    int deleteGroupUser(int groupid,int userid);

    @Delete("delete from groupnumber where groupId = #{groupid}")
    int deleteAllGroupUser(int groupid);
}
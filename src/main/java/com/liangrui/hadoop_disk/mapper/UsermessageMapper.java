package com.liangrui.hadoop_disk.mapper;

import com.liangrui.hadoop_disk.bean.entity.Usermessage;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface UsermessageMapper {
    @Delete({
        "delete from usermessage",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into usermessage (id, statue, ",
        "toUserId, fromUserId, ",
        "time, message)",
        "values (#{id,jdbcType=INTEGER}, #{statue,jdbcType=INTEGER}, ",
        "#{touserid,jdbcType=INTEGER}, #{fromuserid,jdbcType=INTEGER}, ",
        "#{time,jdbcType=VARCHAR}, #{message,jdbcType=LONGVARCHAR})"
    })
    int insert(Usermessage record);



    @Select({
        "select",
        "id, statue, toUserId, fromUserId, time, message",
        "from usermessage",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
        @Result(column="statue", property="statue", jdbcType= JdbcType.INTEGER),
        @Result(column="toUserId", property="touserid", jdbcType= JdbcType.INTEGER),
        @Result(column="fromUserId", property="fromuserid", jdbcType= JdbcType.INTEGER),
        @Result(column="time", property="time", jdbcType= JdbcType.VARCHAR),
        @Result(column="message", property="message", jdbcType= JdbcType.LONGVARCHAR)
    })
    Usermessage selectByPrimaryKey(Integer id);

    @Update({
        "update usermessage",
        "set statue = #{statue,jdbcType=INTEGER},",
          "toUserId = #{touserid,jdbcType=INTEGER},",
          "fromUserId = #{fromuserid,jdbcType=INTEGER},",
          "time = #{time,jdbcType=VARCHAR},",
          "message = #{message,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(Usermessage record);

    @Update({
        "update usermessage",
        "set statue = #{statue,jdbcType=INTEGER},",
          "toUserId = #{touserid,jdbcType=INTEGER},",
          "fromUserId = #{fromuserid,jdbcType=INTEGER},",
          "time = #{time,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Usermessage record);

    @Select("SELECT * from usermessage where  toUserId = #{userid}  and statue=0 ")
    List<Usermessage> findnoreadmessage(int userid);

    @Select("select * from usermessage where  (fromUserId=#{fromid} and toUserId=#{toid}) or (fromUserId=#{toid} and toUserId=#{fromid} ) " +
            "ORDER BY time DESC LIMIT #{index} , #{limit}")
    List<Usermessage> findchatlog(@Param("index") Integer index, @Param("limit") Integer limit,
                                  @Param("fromid") int fromid,@Param("toid") int toid);



}
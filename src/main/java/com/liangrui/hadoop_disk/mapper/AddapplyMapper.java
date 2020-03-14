package com.liangrui.hadoop_disk.mapper;

import com.liangrui.hadoop_disk.bean.entity.Addapply;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface AddapplyMapper {
    @Delete({
            "delete from addapply",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
            "insert into addapply (id, groupid, ",
            "formid, toid, time, ",
            "statue, message)",
            "values (#{id,jdbcType=INTEGER}, #{groupid,jdbcType=INTEGER}, ",
            "#{formid,jdbcType=INTEGER}, #{toid,jdbcType=INTEGER}, #{time,jdbcType=VARCHAR}, ",
            "#{statue,jdbcType=INTEGER}, #{message,jdbcType=VARCHAR})"
    })
    int insert(Addapply record);


    @Select({
            "select",
            "id, groupid, formid, toid, time, statue, message",
            "from addapply",
            "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="groupid", property="groupid", jdbcType=JdbcType.INTEGER),
            @Result(column="formid", property="formid", jdbcType=JdbcType.INTEGER),
            @Result(column="toid", property="toid", jdbcType=JdbcType.INTEGER),
            @Result(column="time", property="time", jdbcType=JdbcType.VARCHAR),
            @Result(column="statue", property="statue", jdbcType=JdbcType.INTEGER),
            @Result(column="message", property="message", jdbcType=JdbcType.VARCHAR)
    })
    Addapply selectByPrimaryKey(Integer id);


    @Update({
            "update addapply",
            "set groupid = #{groupid,jdbcType=INTEGER},",
            "formid = #{formid,jdbcType=INTEGER},",
            "toid = #{toid,jdbcType=INTEGER},",
            "time = #{time,jdbcType=VARCHAR},",
            "statue = #{statue,jdbcType=INTEGER},",
            "message = #{message,jdbcType=VARCHAR}",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Addapply record);

    @Select("select * from addapply where toid=#{toid}")
    List<Addapply> findAllAddapplybyUserid(int toid);

    @Delete("delete from addapply where groupid=#{groupid}")
    int deleteByGroupid(int groupid);
}
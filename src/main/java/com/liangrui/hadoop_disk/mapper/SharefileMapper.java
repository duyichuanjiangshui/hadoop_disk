package com.liangrui.hadoop_disk.mapper;


import com.liangrui.hadoop_disk.bean.entity.Sharefile;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface SharefileMapper {
    @Delete({
            "delete from sharefile",
            "where shareId = #{shareid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer shareid);

    @Insert({
            "insert into sharefile (shareId, userId, ",
            "shareTime, password, ",
            "startTime, shareurl, ",
            "statue)",
            "values (#{shareid,jdbcType=INTEGER}, #{userid,jdbcType=INTEGER}, ",
            "#{sharetime,jdbcType=VARCHAR}, #{password,jdbcType=CHAR}, ",
            "#{starttime,jdbcType=VARCHAR}, #{shareurl,jdbcType=VARCHAR}, ",
            "#{statue,jdbcType=INTEGER})"
    })
    int insert(Sharefile record);


    @Select({
            "select",
            "shareId, userId, shareTime, password, startTime, shareurl, statue",
            "from sharefile",
            "where shareId = #{shareid,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="shareId", property="shareid", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="userId", property="userid", jdbcType=JdbcType.INTEGER),
            @Result(column="shareTime", property="sharetime", jdbcType=JdbcType.VARCHAR),
            @Result(column="password", property="password", jdbcType=JdbcType.CHAR),
            @Result(column="startTime", property="starttime", jdbcType=JdbcType.VARCHAR),
            @Result(column="shareurl", property="shareurl", jdbcType=JdbcType.VARCHAR),
            @Result(column="statue", property="statue", jdbcType=JdbcType.INTEGER)
    })
    Sharefile selectByPrimaryKey(Integer shareid);


    @Update({
            "update sharefile",
            "set userId = #{userid,jdbcType=INTEGER},",
            "shareTime = #{sharetime,jdbcType=VARCHAR},",
            "password = #{password,jdbcType=CHAR},",
            "startTime = #{starttime,jdbcType=VARCHAR},",
            "shareurl = #{shareurl,jdbcType=VARCHAR},",
            "statue = #{statue,jdbcType=INTEGER}",
            "where shareId = #{shareid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Sharefile record);

    @Select("select * from sharefile where shareurl = #{shareurl}")
    Sharefile fildFileByshareUrl(String shareurl);
    @Select("select * from sharefile where userId = #{userid}")
    List<Sharefile> fildFileByUserid(int userid);

}
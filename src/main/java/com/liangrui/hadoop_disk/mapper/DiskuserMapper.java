package com.liangrui.hadoop_disk.mapper;


import com.liangrui.hadoop_disk.bean.entity.Diskuser;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface DiskuserMapper {

    @Delete({
            "delete from diskuser",
            "where userId = #{userid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer userid);

    @Insert({
            "insert into diskuser (userId, rootfolderid, ",
            "sign, password, ",
            "name, phone, imgPath, ",
            "email, statue, createTime)",
            "values (#{userid,jdbcType=INTEGER}, #{rootfolderid,jdbcType=VARCHAR}, ",
            "#{sign,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, ",
            "#{name,jdbcType=VARCHAR}, #{phone,jdbcType=VARCHAR}, #{imgpath,jdbcType=VARCHAR}, ",
            "#{email,jdbcType=VARCHAR}, #{statue,jdbcType=INTEGER}, #{createtime,jdbcType=VARCHAR})"
    })
    int insert(Diskuser record);

    @Select({
            "select",
            "userId, rootfolderid, sign, password, name, phone, imgPath, email, statue, createTime",
            "from diskuser",
            "where userId = #{userid,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="userId", property="userid", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="rootfolderid", property="rootfolderid", jdbcType=JdbcType.VARCHAR),
            @Result(column="sign", property="sign", jdbcType=JdbcType.VARCHAR),
            @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
            @Result(column="imgPath", property="imgpath", jdbcType=JdbcType.VARCHAR),
            @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
            @Result(column="statue", property="statue", jdbcType=JdbcType.INTEGER),
            @Result(column="createTime", property="createtime", jdbcType=JdbcType.VARCHAR)
    })
    Diskuser selectByPrimaryKey(Integer userid);

    @Update({
            "update diskuser",
            "set rootfolderid = #{rootfolderid,jdbcType=VARCHAR},",
            "sign = #{sign,jdbcType=VARCHAR},",
            "password = #{password,jdbcType=VARCHAR},",
            "name = #{name,jdbcType=VARCHAR},",
            "phone = #{phone,jdbcType=VARCHAR},",
            "imgPath = #{imgpath,jdbcType=VARCHAR},",
            "email = #{email,jdbcType=VARCHAR},",
            "statue = #{statue,jdbcType=INTEGER},",
            "createTime = #{createtime,jdbcType=VARCHAR}",
            "where userId = #{userid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Diskuser record);
    @Select("select * from diskuser where `name`=#{name}")
    Diskuser selectbyname(String name);

    @Select("select * from diskuser where `name` like '%${name}%'")
    List<Diskuser > selectusersbyname(String name);
}
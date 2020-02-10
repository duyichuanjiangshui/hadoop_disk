package com.liangrui.hadoop_disk.mapper;


import com.liangrui.hadoop_disk.bean.entity.Diskuser;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface DiskuserMapper {
    @Delete({
        "delete from diskuser",
        "where userId = #{userid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer userid);

    @Insert({
        "insert into diskuser (userId, password, ",
        "name, phone, imgPath, ",
        "email, type, createTime)",
        "values (#{userid,jdbcType=INTEGER}, #{password,jdbcType=VARCHAR}, ",
        "#{name,jdbcType=VARCHAR}, #{phone,jdbcType=VARCHAR}, #{imgpath,jdbcType=VARCHAR}, ",
        "#{email,jdbcType=VARCHAR}, #{type,jdbcType=INTEGER}, #{createtime,jdbcType=VARCHAR})"
    })
    int insert(Diskuser record);



    @Select({
        "select",
        "userId, password, name, phone, imgPath, email, type, createTime",
        "from diskuser",
        "where userId = #{userid,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="userId", property="userid", jdbcType= JdbcType.INTEGER, id=true),
        @Result(column="password", property="password", jdbcType= JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType= JdbcType.VARCHAR),
        @Result(column="phone", property="phone", jdbcType= JdbcType.VARCHAR),
        @Result(column="imgPath", property="imgpath", jdbcType= JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType= JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType= JdbcType.INTEGER),
        @Result(column="createTime", property="createtime", jdbcType= JdbcType.VARCHAR)
    })
    Diskuser selectByPrimaryKey(Integer userid);



    @Update({
        "update diskuser",
        "set password = #{password,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=VARCHAR},",
          "phone = #{phone,jdbcType=VARCHAR},",
          "imgPath = #{imgpath,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=INTEGER},",
          "createTime = #{createtime,jdbcType=VARCHAR}",
        "where userId = #{userid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Diskuser record);
}
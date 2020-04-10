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

    @Update("<script> update diskuser<set ><if test=\"rootfolderid != null\" >    rootfolderid = #{rootfolderid,jdbcType=VARCHAR},  </if> <if test=\"sign != null\" >sign = #{sign,jdbcType=VARCHAR},</if><if test=\"password != null\" >password = #{password,jdbcType=VARCHAR},</if><if test=\"name != null\" >name = #{name,jdbcType=VARCHAR},</if><if test=\"phone != null\" >phone = #{phone,jdbcType=VARCHAR},</if><if test=\"imgpath != null\" >imgPath = #{imgpath,jdbcType=VARCHAR},</if><if test=\"email != null\" >email = #{email,jdbcType=VARCHAR},</if><if test=\"statue != null\" >statue = #{statue,jdbcType=INTEGER},</if><if test=\"createtime != null\" >createTime " +
            "= #{createtime,jdbcType=VARCHAR},</if></set>where userId = #{userid,jdbcType=INTEGER}</script>")
    int updateByPrimaryKey(Diskuser record);
    @Select("select * from diskuser where `name`=#{name} or email=#{name}")
    Diskuser selectbyname(String name);
    @Update("update diskuser set password=#{password} where email=#{email}")
    int updatepassword(String password,String email);

    @Select("select password from diskuser where userId = #{userid}")
    String getpasswordbydiskuser(int userid);
    @Select("select * from diskuser where `name` like '%${name}%'")
    List<Diskuser > selectusersbyname(String name);
    @Select("SELECT userId from diskuser where name=#{name}")
    Integer getuserId(String name);
    @Select("SELECT userId from diskuser where email=#{email}")
    Integer getuserIdbyemail(String email);
    @Select("select * from diskuser where userId in (select userId from groupnumber where groupId = #{groupid})")
    List<Diskuser> selectuserformgroupid (int groupid);
}
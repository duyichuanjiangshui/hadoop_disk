package com.liangrui.hadoop_disk.mapper;


import com.liangrui.hadoop_disk.bean.entity.Upload;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
@Mapper
public interface UploadMapper {
    @Delete({
        "delete from upload",
        "where uploadLoationId = #{uploadloationid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer uploadloationid);

    @Insert("insert into upload (uploadLoationId, uploadLocation, uploadTime, userId, " +
            "orignalName, uploadType, userNum, sortype, size) values (#{uploadloationid," +
            "jdbcType=INTEGER}, #{uploadlocation,jdbcType=VARCHAR}, #{uploadtime,jdbcType=" +
            "VARCHAR}, #{userid,jdbcType=INTEGER}, #{orignalname,jdbcType=VARCHAR}, #{uploadtype" +
            ",jdbcType=INTEGER}," +
            " #{usernum,jdbcType=INTEGER}, #{sortype,jdbcType=INTEGER}, #{size,jdbcType=REAL})")
    int insert(Upload record);

    @Select({
        "select",
        "uploadLoationId, uploadLocation, uploadTime, userId, orignalName, uploadType, ",
        "userNum, sortype",
        "from upload",
        "where uploadLoationId = #{uploadloationid,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="uploadLoationId", property="uploadloationid", jdbcType= JdbcType.INTEGER, id=true),
        @Result(column="uploadLocation", property="uploadlocation", jdbcType= JdbcType.VARCHAR),
        @Result(column="uploadTime", property="uploadtime", jdbcType= JdbcType.VARCHAR),
        @Result(column="userId", property="userid", jdbcType= JdbcType.INTEGER),
        @Result(column="orignalName", property="orignalname", jdbcType= JdbcType.VARCHAR),
        @Result(column="uploadType", property="uploadtype", jdbcType= JdbcType.INTEGER),
        @Result(column="userNum", property="usernum", jdbcType= JdbcType.INTEGER),
        @Result(column="sortype", property="sortype", jdbcType= JdbcType.INTEGER)
    })
    Upload selectByPrimaryKey(Integer uploadloationid);


    @Update("<script>update upload<set ><if test=\"uploadlocation != null\" >uploadLocation = #{uploadlocation,jdbcType=VARCHAR},</if><if test=\"uploadtime != null\" >uploadTime = #{uploadtime,jdbcType=VARCHAR},</if><if test=\"userid != null\" >userId = #{userid,jdbcType=INTEGER},</if><if test=\"orignalname != null\" >orignalName = #{orignalname,jdbcType=VARCHAR}," +
            "</if><if test=\"uploadtype != null\" >uploadType = #{uploadtype,jdbcType=INTEGER},</if><if test=\"usernum != null\" >userNum = #{usernum,jdbcType=INTEGER},</if><if test=\"sortype != null\" >sortype = #{sortype,jdbcType=INTEGER},</if>" +
            "</set>where uploadLoationId = #{uploadloationid,jdbcType=INTEGER}</script>")
    int updateByPrimaryKey(Upload record);
    @Select({

            "select",

            "uploadLoationId  "+

                    "from upload",

            "where uploadLocation = #{uploadLocation,jdbcType=INTEGER}"

    })



    int selectuploadLocation(String uploadLocation);
}
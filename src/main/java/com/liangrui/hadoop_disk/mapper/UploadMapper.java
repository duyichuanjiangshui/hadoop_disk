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

    @Insert({
        "insert into upload (uploadLoationId, uploadLocation, ",
        "uploadTime, userId, ",
        "orignalName, uploadType, ",
        "userNum)",
        "values (#{uploadloationid,jdbcType=INTEGER}, #{uploadlocation,jdbcType=VARCHAR}, ",
        "#{uploadtime,jdbcType=VARCHAR}, #{userid,jdbcType=INTEGER}, ",
        "#{orignalname,jdbcType=VARCHAR}, #{uploadtype,jdbcType=INTEGER}, ",
        "#{usernum,jdbcType=INTEGER})"
    })
    int insert(Upload record);



    @Select({
        "select",
        "uploadLoationId, uploadLocation, uploadTime, userId, orignalName, uploadType, ",
        "userNum",
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
        @Result(column="userNum", property="usernum", jdbcType= JdbcType.INTEGER)
    })
    Upload selectByPrimaryKey(Integer uploadloationid);

    @Select({
            "select",
            "uploadLoationId  "+
            "from upload",
            "where uploadLocation = #{uploadLocation,jdbcType=INTEGER}"
    })

    int selectuploadLocation(String uploadLocation);


    @Update({
        "update upload",
        "set uploadLocation = #{uploadlocation,jdbcType=VARCHAR},",
          "uploadTime = #{uploadtime,jdbcType=VARCHAR},",
          "userId = #{userid,jdbcType=INTEGER},",
          "orignalName = #{orignalname,jdbcType=VARCHAR},",
          "uploadType = #{uploadtype,jdbcType=INTEGER},",
          "userNum = #{usernum,jdbcType=INTEGER}",
        "where uploadLoationId = #{uploadloationid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Upload record);
}
package com.liangrui.hadoop_disk.mapper;

import com.liangrui.hadoop_disk.bean.entity.Sharedetail;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface SharedetailMapper {
    @Delete({
        "delete from sharedetail",
        "where shareDetailId = #{sharedetailid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer sharedetailid);

    @Insert({
        "insert into sharedetail (shareDetailId, shareId, ",
        "resourceType, resourceTypeId)",
        "values (#{sharedetailid,jdbcType=INTEGER}, #{shareid,jdbcType=INTEGER}, ",
        "#{resourcetype,jdbcType=INTEGER}, #{resourcetypeid,jdbcType=VARCHAR})"
    })
    int insert(Sharedetail record);


    @Select({
        "select",
        "shareDetailId, shareId, resourceType, resourceTypeId",
        "from sharedetail",
        "where shareDetailId = #{sharedetailid,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="shareDetailId", property="sharedetailid", jdbcType= JdbcType.INTEGER, id=true),
        @Result(column="shareId", property="shareid", jdbcType= JdbcType.INTEGER),
        @Result(column="resourceType", property="resourcetype", jdbcType= JdbcType.INTEGER),
        @Result(column="resourceTypeId", property="resourcetypeid", jdbcType= JdbcType.VARCHAR)
    })
    Sharedetail selectByPrimaryKey(Integer sharedetailid);



    @Update({
        "update sharedetail",
        "set shareId = #{shareid,jdbcType=INTEGER},",
          "resourceType = #{resourcetype,jdbcType=INTEGER},",
          "resourceTypeId = #{resourcetypeid,jdbcType=VARCHAR}",
        "where shareDetailId = #{sharedetailid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Sharedetail record);
    @Select("select * from sharedetail where shareId=#{shareid}")
    List<Sharedetail> findAllSharedetailByShareid(int sherid);
    @Delete({
            "delete from sharedetail",
            "where shareId = #{shareId}"
    })
    int deleteBy(Integer shareId);
}
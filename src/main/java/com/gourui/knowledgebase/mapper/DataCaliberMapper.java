package com.gourui.knowledgebase.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DataCaliberMapper {
    @Insert("INSERT INTO data_caliber(uuid,requirement_id,requirement_name,worker_name,extractor_name,department_name,comments,sql) " +
            "VALUES(#{uuid},#{requirement_id},#{requirement_name},#{worker_name},#{extractor_name},#{department_name},#{comments},#{sql})")
    int add(@Param("uuid")String uuid,
            @Param("requirement_id")String requirement_id,
            @Param("requirement_name")String requirement_name,
            @Param("worker_name")String worker_name,
            @Param("extractor_name")String extractor_name,
            @Param("department_name")String department_name,
            @Param("comments")String comments,
            @Param("sql")String sql);
}

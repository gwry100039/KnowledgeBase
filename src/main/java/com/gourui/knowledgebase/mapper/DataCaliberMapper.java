package com.gourui.knowledgebase.mapper;

import com.gourui.knowledgebase.domain.DataCaliber;
import com.gourui.knowledgebase.domain.Org;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DataCaliberMapper {
    @Insert("INSERT INTO data_caliber(uuid,requirement_id,requirement_name,worker_name,extractor_name,department_name,requirement_desc,comments,sql) " +
            "VALUES(#{uuid},#{requirement_id},#{requirement_name},#{worker_name},#{extractor_name},#{department_name},#{requirement_desc},#{comments},#{sql})")
    int add(@Param("uuid")String uuid,
            @Param("requirement_id")String requirement_id,
            @Param("requirement_name")String requirement_name,
            @Param("worker_name")String worker_name,
            @Param("extractor_name")String extractor_name,
            @Param("department_name")String department_name,
            @Param("requirement_desc") String requirement_desc,
            @Param("comments")String comments,
            @Param("sql")String sql);

    @Select("select uuid," +
            "requirement_id as requirementId," +
            "requirement_name as requirementName," +
            "worker_name workerName," +
            "extractor_name extractorName," +
            "department_name departmentName," +
            "requirement_desc requirementDesc," +
            "comments," +
            "sql from Data_Caliber where rownum <= 5")
    List<DataCaliber> selectList();
}

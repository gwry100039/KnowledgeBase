package com.gourui.knowledgebase.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TableColumnInfoMapper {
    @Insert("INSERT INTO table_column_info(uuid,column_name,table_name,column_comment,system_name,requirement_id) " +
            "VALUES(sys_uuid(),#{column_name},#{table_name},#{column_comment},#{system_name},#{requirement_id})")
    int add(@Param("column_name")String column_name,
            @Param("table_name")String table_name,
            @Param("column_comment")String column_comment,
            @Param("system_name")String system_name,
            @Param("requirement_id")String requirement_id);
}

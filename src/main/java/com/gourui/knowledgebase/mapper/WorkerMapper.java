package com.gourui.knowledgebase.mapper;

import com.gourui.knowledgebase.domain.Worker;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WorkerMapper {
    @Select("select id,name from p_workers")
    List<Worker> selectList();
}

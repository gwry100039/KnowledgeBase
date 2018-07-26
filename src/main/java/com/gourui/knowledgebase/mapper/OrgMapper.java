package com.gourui.knowledgebase.mapper;

import com.gourui.knowledgebase.domain.Org;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrgMapper {
    @Select("select id,name from p_org")
    List<Org> selectList();
}

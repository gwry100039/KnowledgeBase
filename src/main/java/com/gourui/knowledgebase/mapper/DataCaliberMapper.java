package com.gourui.knowledgebase.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DataCaliberMapper {
    @Insert("INSERT INTO account(name,money) VALUES(#{name},#{money})")
    int add(@Param("name")String name,
            @Param("money")double money);
}

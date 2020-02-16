package com.sjl.community.mapper;

import com.sjl.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author song
 * @create 2020/2/16 18:24
 */
@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title, description, tags, gmt_create, gmt_modified, creator) values(#{title}, #{description}, #{tags}, #{gmtCreate}, #{gmtModified}, #{creator})")
    void insertQuestion(Question question);
}

package com.sjl.community.mapper;

import com.sjl.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author song
 * @create 2020/2/16 18:24
 */
@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title, description, tags, gmt_create, gmt_modified, creator) values(#{title}, #{description}, #{tags}, #{gmtCreate}, #{gmtModified}, #{creator})")
    void insertQuestion(Question question);

    @Select("select * from question limit #{offerIndex}, #{pageSize}")
    List<Question> findAll(@Param("offerIndex") int offerIndex, @Param("pageSize") int pageSize);

    @Select("select count(1) from question")
    int getCount();

    @Select("select count(1) from question where creator = #{id}")
    int getCountByCreatorId(Long id);

    @Select("select * from question where creator = #{id} limit #{offerIndex}, #{pageSize} ")
    List<Question> findByCreatorId(int offerIndex, Integer pageSize, Long id);
}

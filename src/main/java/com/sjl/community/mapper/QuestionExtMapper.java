package com.sjl.community.mapper;

import com.sjl.community.dto.QuestionQueryDto;
import com.sjl.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    void addViewCount(Question question);

    void addCommentCount(Question question);

    List<Question> findByTagsREGEXP(Question question);

//    int countBySearch(String search);
//
//    int countByTag(String tag);
//
//    int countBySort(String sort, Long time);

    int countByCondition(QuestionQueryDto questionQuery);

    List<Question> findByCondition(QuestionQueryDto questionQuery);
}
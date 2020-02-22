package com.sjl.community.mapper;

import com.sjl.community.model.Question;
import java.util.List;

public interface QuestionExtMapper {
    void addViewCount(Question question);

    void addCommentCount(Question question);

    List<Question> findByREGEXP(Question question);
}
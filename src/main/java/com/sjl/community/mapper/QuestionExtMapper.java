package com.sjl.community.mapper;

import com.sjl.community.model.Question;

public interface QuestionExtMapper {
    void addViewCount(Question question);
}
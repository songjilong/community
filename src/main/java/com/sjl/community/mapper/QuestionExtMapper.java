package com.sjl.community.mapper;

import com.sjl.community.model.Question;
import java.util.List;

public interface QuestionExtMapper {
    void addViewCount(Question question);

    void addCommentCount(Question question);

    List<Question> findByTagsREGEXP(Question question);

    List<Question> findDescByTopAndGmt(Long id, Integer offerIndex, Integer pageSize);

    List<Question> findBySearchREGEXP(String search, Integer offerIndex, Integer pageSize);

    int countBySearch(String search);
}
package com.sjl.community.mapper;

import com.sjl.community.model.Comment;

public interface CommentExtMapper {
    void addCommentCount(Comment comment);
}
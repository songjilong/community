package com.sjl.community.service;

import com.sjl.community.enums.CommentTypeEnum;
import com.sjl.community.exception.CustomizeErrorCode;
import com.sjl.community.exception.CustomizeException;
import com.sjl.community.mapper.CommentMapper;
import com.sjl.community.mapper.QuestionExtMapper;
import com.sjl.community.mapper.QuestionMapper;
import com.sjl.community.model.Comment;
import com.sjl.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author song
 * @create 2020/2/20 19:15
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Transactional
    public void insert(Comment comment) {
        //评论的父级不存在
        if (comment.getParentId() == null) {
            throw new CustomizeException(CustomizeErrorCode.COMMENT_PARENT_NOT_EXIST);
        }
        //评论类型不存在
        if (comment.getType() == null || CommentTypeEnum.isNotExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_NOT_EXIST);
        }
        //问题的评论
        if (comment.getType() == CommentTypeEnum.TYPE_QUESTION.getType()) {
            Question dbQuestion = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (dbQuestion == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            //数据库中插入评论
            commentMapper.insertSelective(comment);
            dbQuestion.setCommentCount(1);
            //评论数+1
            questionExtMapper.addCommentCount(dbQuestion);
        }
        //回复的评论
        else {
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            //数据库插入评论
            commentMapper.insertSelective(comment);
        }
    }
}

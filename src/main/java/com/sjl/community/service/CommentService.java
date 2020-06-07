package com.sjl.community.service;

import com.sjl.community.dto.CommentDto;
import com.sjl.community.enums.CommentTypeEnum;
import com.sjl.community.enums.NotificationStatusEnum;
import com.sjl.community.enums.NotificationTypeEnum;
import com.sjl.community.exception.CustomizeErrorCode;
import com.sjl.community.exception.CustomizeException;
import com.sjl.community.mapper.*;
import com.sjl.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private CommentExtMapper commentExtMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional(rollbackFor = Exception.class)
    public void insertComment(Comment comment, User user) {
        //评论的父级不存在
        if (comment.getParentId() == 0 || comment.getParentId() == null) {
            throw new CustomizeException(CustomizeErrorCode.COMMENT_PARENT_NOT_EXIST);
        }
        //评论类型不存在
        if (comment.getType() == null || CommentTypeEnum.isNotExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_NOT_EXIST);
        }
        //问题的回复
        if (comment.getType().equals(CommentTypeEnum.TYPE_QUESTION.getType())) {
            Question dbQuestion = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (dbQuestion == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            //数据库中插入评论
            commentMapper.insertSelective(comment);
            dbQuestion.setCommentCount(1);
            //评论数+1
            questionExtMapper.addCommentCount(dbQuestion);

            //创建通知
            createNotification(comment.getCommentator(), user.getName(), dbQuestion.getCreator(), NotificationTypeEnum.REPLY_QUESTION.getType(), dbQuestion.getId(), dbQuestion.getTitle());
        }
        //评论的回复
        else {
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            //数据库插入评论
            commentMapper.insertSelective(comment);
            //增加评论的回复数
            Comment parentComment = new Comment();
            parentComment.setCommentCount(1L);
            parentComment.setId(comment.getParentId());
            commentExtMapper.addCommentCount(parentComment);

            //创建通知
            createNotification(comment.getCommentator(), user.getName(), dbComment.getCommentator(), NotificationTypeEnum.REPLY_COMMENT.getType(), question.getId(), question.getTitle());
        }
    }

    /**
     * 创建通知
     * @param notifierId 发送人id
     * @param notifyName 发送人姓名
     * @param receiverId 接收人id
     * @param type 通知类型
     * @param targetId 问题id
     * @param targetTitle 问题标题
     */
    public void createNotification(Long notifierId, String notifyName, Long receiverId, int type, Long targetId, String targetTitle){
        if(notifierId.equals(receiverId)){
            return;
        }
        Notification notification = new Notification();
        notification.setNotifierId(notifierId);
        notification.setNotifyName(notifyName);
        notification.setReceiverId(receiverId);
        notification.setTargetId(targetId);
        notification.setTargetTitle(targetTitle);
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setType(type);
        notification.setGmtCreate(System.currentTimeMillis());
        notificationMapper.insertSelective(notification);
    }

    /**
     * 根据id和类型查询评论数据
     *
     * @param id
     * @return
     */
    public List<CommentDto> findByQuestionId(Long id, CommentTypeEnum typeEnum) {
        //获取该问题的评论列表
        CommentExample example = new CommentExample();
        example.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(typeEnum.getType());
        //根据创建时间降序排列
        example.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(example);

        if (comments.size() == 0) {
            return new ArrayList<>();
        }

        //获取评论对应的创建人id
        List<Long> idList = comments.stream().map(Comment::getCommentator).distinct().collect(Collectors.toList());

        //通过id获取user对象
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(idList);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));

        //将评论、用户放到传输对象中
        return comments.stream().map(comment -> {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment, commentDto);
            commentDto.setUser(userMap.get(comment.getCommentator()));
            return commentDto;
        }).collect(Collectors.toList());
    }
}

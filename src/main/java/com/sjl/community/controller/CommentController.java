package com.sjl.community.controller;

import com.sjl.community.dto.CommentCreateDto;
import com.sjl.community.dto.ResultDto;
import com.sjl.community.exception.CustomizeErrorCode;
import com.sjl.community.model.Comment;
import com.sjl.community.model.User;
import com.sjl.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author song
 * @create 2020/2/19 22:47
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comment")
    @ResponseBody
    public Object comment(@RequestBody CommentCreateDto commentDto, HttpServletRequest request){
        //获取用户
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return ResultDto.errorOf(CustomizeErrorCode.USER_NOT_LOGIN);
        }
        if(commentDto == null || StringUtils.isBlank(commentDto.getContent())){
            return ResultDto.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDto.getParentId());
        comment.setContent(commentDto.getContent());
        comment.setType(commentDto.getType());
        comment.setCommentator(user.getId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setLikeCount(0L);
        commentService.insertComment(comment);
        return ResultDto.okOf();
    }
}

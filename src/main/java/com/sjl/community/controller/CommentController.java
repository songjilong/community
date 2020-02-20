package com.sjl.community.controller;

import com.sjl.community.dto.CommentDto;
import com.sjl.community.mapper.CommentMapper;
import com.sjl.community.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author song
 * @create 2020/2/19 22:47
 */
@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    @PostMapping("/comment")
    @ResponseBody
    public Object post(@RequestBody CommentDto commentDto){
        Comment comment = new Comment();
        comment.setParentId(commentDto.getParentId());
        comment.setContent(commentDto.getContent());
        comment.setType(commentDto.getType());
        comment.setCommentator(1L);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setLikeCount(1L);
        commentMapper.insert(comment);
        return null;
    }
}

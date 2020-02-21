package com.sjl.community.controller;

import com.sjl.community.dto.CommentDto;
import com.sjl.community.dto.QuestionDto;
import com.sjl.community.service.CommentService;
import com.sjl.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author song
 * @create 2020/2/18 20:18
 */
@Controller
@RequestMapping("question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    /**
     * 根据问题id跳转到详情页面
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public String showQuestion(@PathVariable("id") Long id, Model model, HttpServletRequest request){
        //根据问题id查询详情
        QuestionDto questionDto = questionService.findById(id);
        //根据问题id查询评论详情
        List<CommentDto> commentDtos = commentService.findByQuestionId(id);
        //存入model作用域
        model.addAttribute("questionDto", questionDto);
        model.addAttribute("commentDtos", commentDtos);
        //增加阅读数
        questionService.addViewCount(id);
        return "question";
    }
}

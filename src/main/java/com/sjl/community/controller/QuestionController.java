package com.sjl.community.controller;

import com.sjl.community.dto.QuestionDto;
import com.sjl.community.exception.CustomizeErrorCode;
import com.sjl.community.exception.CustomizeException;
import com.sjl.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author song
 * @create 2020/2/18 20:18
 */
@Controller
@RequestMapping("question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 根据问题id跳转到详情页面
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public String showQuestion(@PathVariable("id") Long id, Model model, HttpServletRequest request){
        //根据问题id查询详情
        QuestionDto questionDto = questionService.findById(id);
        //存入model作用域
        model.addAttribute("questionDto", questionDto);
        //增加阅读数
        questionService.addViewCount(id);
        return "question";
    }
}

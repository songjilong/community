package com.sjl.community.controller;

import com.sjl.community.dto.PaginationDto;
import com.sjl.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author song
 * @create 2020/2/13 19:49
 */
@Controller
public class IndexController {


    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                        @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        //添加问题信息
        PaginationDto pageInfo = questionService.findAll(pageNum, pageSize);

        //添加到model作用域
        model.addAttribute("pageInfo", pageInfo);
        return "index";
    }
}

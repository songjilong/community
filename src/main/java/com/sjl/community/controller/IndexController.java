package com.sjl.community.controller;

import com.sjl.community.cache.HotTagCache;
import com.sjl.community.dto.PaginationDto;
import com.sjl.community.dto.QuestionDto;
import com.sjl.community.service.NotificationService;
import com.sjl.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author song
 * @create 2020/2/13 19:49
 */
@Controller
public class IndexController {


    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private HotTagCache hotTagCache;

    //热门标签
//    private

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                        @RequestParam(value = "pageSize", defaultValue = "8") Integer pageSize) {
        //添加问题信息
        PaginationDto<QuestionDto> pageInfo = questionService.findAll(pageNum, pageSize);
        //添加到model作用域
        model.addAttribute("pageInfo", pageInfo);
        List<String> topTags = hotTagCache.getTopTags();
        model.addAttribute("hotTags", topTags);
        return "index";
    }

    @GetMapping("/search")
    public String search(Model model,
                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                         @RequestParam(value = "pageSize", defaultValue = "8") Integer pageSize,
                         @RequestParam(value = "search", required = false) String search){
        //添加问题信息
        PaginationDto<QuestionDto> pageInfo = questionService.findBySearch(pageNum, pageSize, search);
        //添加到model作用域
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("search", search);
        List<String> topTags = hotTagCache.getTopTags();
        model.addAttribute("hotTags", topTags);
        return "index";
    }

    @GetMapping("/tag")
    public String tag(Model model,
                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                         @RequestParam(value = "pageSize", defaultValue = "8") Integer pageSize,
                         @RequestParam(value = "tag", required = false) String tag){
        //添加问题信息
        PaginationDto<QuestionDto> pageInfo = questionService.findByTag(pageNum, pageSize, tag);
        //添加到model作用域
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("tag", tag);
        List<String> topTags = hotTagCache.getTopTags();
        model.addAttribute("hotTags", topTags);
        return "index";
    }
}

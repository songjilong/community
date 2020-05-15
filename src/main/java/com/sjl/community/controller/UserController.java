package com.sjl.community.controller;

import com.sjl.community.dto.PaginationDto;
import com.sjl.community.dto.QuestionDto;
import com.sjl.community.dto.QuestionQueryDto;
import com.sjl.community.model.User;
import com.sjl.community.service.NotificationService;
import com.sjl.community.service.QuestionService;
import com.sjl.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author song
 * @create 2020/2/27 20:21
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/user/{id}")
    public String getInformationById(@PathVariable Long id, Model model,
                                     @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "8", required = false) Integer pageSize) {
        User user = userService.findById(id);
        QuestionQueryDto queryDto = QuestionQueryDto.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .creatorId(user.getId())
                .build();
        //查询当前用户的问题列表
        PaginationDto<QuestionDto> pageInfo = questionService.findByCondition(queryDto);
        model.addAttribute("userInfo", user);
        model.addAttribute("pageInfo", pageInfo);
        return "user";
    }
}

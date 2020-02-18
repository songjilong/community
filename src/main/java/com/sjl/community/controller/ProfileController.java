package com.sjl.community.controller;

import com.sjl.community.dto.PaginationDto;
import com.sjl.community.model.User;
import com.sjl.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author song
 * @create 2020/2/18 15:39
 */
@Controller
@RequestMapping("profile")
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("{section}")
    public String profile(@PathVariable("section") String section,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        //从session中获取user
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }

        if ("questions".equals(section)) {
            model.addAttribute("section", "我的提问");
        } else if ("replies".equals(section)) {
            model.addAttribute("section", "最新回复");
        }

        //查询当前用户的问题列表
        PaginationDto pageInfo = questionService.findByCreatorId(pageNum, pageSize, user.getId());
        model.addAttribute("pageInfo", pageInfo);

        return "profile";
    }
}

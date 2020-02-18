package com.sjl.community.controller;

import com.sjl.community.mapper.QuestionMapper;
import com.sjl.community.mapper.UserMapper;
import com.sjl.community.model.Question;
import com.sjl.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author song
 * @create 2020/2/16 16:19
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tags") String tags,
            Model model,
            HttpServletRequest request) {
        //从session中获取user
        User user = (User) request.getSession().getAttribute("user");

        //错误信息
        if (user == null) {
            model.addAttribute("error", "用户未登陆");
            return "publish";
        }
        //判断是否填入信息
        if (StringUtils.isBlank(title)) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (StringUtils.isBlank(description)) {
            model.addAttribute("error", "描述信息不能为空");
            return "publish";
        }
        if (StringUtils.isBlank(tags)) {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tags", tags);

        //添加数据
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTags(tags);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        //存入数据库
        questionMapper.insertQuestion(question);
        //发布成功，返回主页面
        return "redirect:/";
    }
}

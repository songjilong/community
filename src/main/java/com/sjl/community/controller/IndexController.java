package com.sjl.community.controller;

import com.sjl.community.cache.HotTagCache;
import com.sjl.community.dto.PaginationDto;
import com.sjl.community.dto.QuestionDto;
import com.sjl.community.dto.QuestionQueryDto;
import com.sjl.community.service.NotificationService;
import com.sjl.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/")
    public String test(Model model,
                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", defaultValue = "8") Integer pageSize,
                       @RequestParam(value = "search", required = false) String search,
                       @RequestParam(value = "tag", required = false) String tag,
                       @RequestParam(value = "sort", required = false) String sort){
        QuestionQueryDto queryDto = new QuestionQueryDto();
        queryDto.setPageNum(pageNum);
        queryDto.setPageSize(pageSize);
        queryDto.setSearch(search);
        queryDto.setTag(tag);
        queryDto.setSort(sort);
        PaginationDto<QuestionDto> pageInfo = questionService.findByCondition(queryDto);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("search", search);
        model.addAttribute("tag", tag);
        model.addAttribute("sortType", queryDto.getSort());
        //热门标签
        List<String> topTags = hotTagCache.getTopTags();
        model.addAttribute("hotTags", topTags);
        return "index";
    }
}

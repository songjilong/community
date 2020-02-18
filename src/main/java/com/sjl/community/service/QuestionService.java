package com.sjl.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sjl.community.dto.PaginationDto;
import com.sjl.community.dto.QuestionDto;
import com.sjl.community.mapper.QuestionMapper;
import com.sjl.community.mapper.UserMapper;
import com.sjl.community.model.Question;
import com.sjl.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author song
 * @create 2020/2/17 13:33
 */
@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDto findAll(Integer pageNum, Integer pageSize) {

        //问题列表信息
        List<QuestionDto> questionDtos = new ArrayList<>();

        //返回的页面信息+分页信息
        PaginationDto pageInfo = new PaginationDto();

        //计算总记录数
        int totalCount = questionMapper.getCount();

        if(totalCount == 0){
            return pageInfo;
        }

        //设置分页信息
        pageInfo.setInfo(totalCount, pageNum, pageSize);

        //限定当前页范围
        if(pageNum < 1){
            pageNum = 1;
        }else if(pageNum > pageInfo.getTotalPage()){
            pageNum = pageInfo.getTotalPage();
        }

        //获取截取的位置
        int offerIndex = (pageNum - 1) * pageSize;
        List<Question> questions = questionMapper.findAll(offerIndex, pageSize);

        //先遍历获取所有的问题
        for(Question question : questions){
            QuestionDto questionDto = new QuestionDto();
            //根据问题的发起人id获取user对象
            User user = userMapper.findById(question.getCreator());
            //将数据放到questionDto对象
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            //添加到集合
            questionDtos.add(questionDto);
        }

        //添加问题信息
        pageInfo.setList(questionDtos);

        return pageInfo;
    }

    public PaginationDto findByCreatorId(Integer pageNum, Integer pageSize, Long id) {
        //问题列表信息
        List<QuestionDto> questionDtos = new ArrayList<>();

        //返回的页面信息+分页信息
        PaginationDto pageInfo = new PaginationDto();

        //计算总记录数
        int totalCount = questionMapper.getCountByCreatorId(id);

        if(totalCount == 0){
            return pageInfo;
        }

        //设置分页信息
        pageInfo.setInfo(totalCount, pageNum, pageSize);

        //限定当前页范围
        if(pageNum < 1){
            pageNum = 1;
        }else if(pageNum > pageInfo.getTotalPage()){
            pageNum = pageInfo.getTotalPage();
        }

        //获取截取的位置
        int offerIndex = (pageNum - 1) * pageSize;
        List<Question> questions = questionMapper.findByCreatorId(offerIndex, pageSize, id);

        //先遍历获取所有的问题
        for(Question question : questions){
            QuestionDto questionDto = new QuestionDto();
            //根据问题的发起人id获取user对象
            User user = userMapper.findById(question.getCreator());
            //将数据放到questionDto对象
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            //添加到集合
            questionDtos.add(questionDto);
        }

        //添加问题信息
        pageInfo.setList(questionDtos);

        return pageInfo;
    }
}

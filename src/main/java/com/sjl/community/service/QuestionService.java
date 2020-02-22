package com.sjl.community.service;

import com.sjl.community.dto.PaginationDto;
import com.sjl.community.dto.QuestionDto;
import com.sjl.community.exception.CustomizeErrorCode;
import com.sjl.community.exception.CustomizeException;
import com.sjl.community.mapper.QuestionExtMapper;
import com.sjl.community.mapper.QuestionMapper;
import com.sjl.community.mapper.UserMapper;
import com.sjl.community.model.Question;
import com.sjl.community.model.QuestionExample;
import com.sjl.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private QuestionExtMapper questionExtMapper;

    /**
     * 查询分页信息
     *
     * @param pageNum
     * @param pageSize
     * @param totalCount
     * @param id
     * @return
     */
    public PaginationDto getPageInfo(Integer pageNum, Integer pageSize, int totalCount, Long id) {
        //问题列表信息
        List<QuestionDto> questionDtos = new ArrayList<>();
        //返回的页面信息+分页信息
        PaginationDto pageInfo = new PaginationDto();
        //如果总记录数为0，直接返回一个空数据
        if (totalCount == 0) {
            return pageInfo;
        }
        //设置分页信息
        pageInfo.setInfo(totalCount, pageNum, pageSize);
        //限定当前页范围
        if (pageNum < 1) {
            pageNum = 1;
        } else if (pageNum > pageInfo.getTotalPage()) {
            pageNum = pageInfo.getTotalPage();
        }
        //获取截取的位置
        int offerIndex = (pageNum - 1) * pageSize;
        List<Question> questions;
        //判断id存不存在，来决定分页的方式
        QuestionExample questionExample = new QuestionExample();
        if (id != null) {
            questionExample.createCriteria().andCreatorEqualTo(id);
            questionExample.setOrderByClause("gmt_create desc");
        }
        //添加分页
        questions = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offerIndex, pageSize));
        //先遍历获取所有的问题
        for (Question question : questions) {
            QuestionDto questionDto = new QuestionDto();
            //根据问题的发起人id获取user对象
            User user = userMapper.selectByPrimaryKey(question.getCreator());
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

    /**
     * 查询所有问题 的分页信息
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PaginationDto findAll(Integer pageNum, Integer pageSize) {
        //计算总记录数
        int totalCount = (int) questionMapper.countByExample(null);
        //查询所有的，不需要id
        return getPageInfo(pageNum, pageSize, totalCount, null);
    }

    /**
     * 查询某个发起人的问题 的分页信息
     *
     * @param pageNum
     * @param pageSize
     * @param id
     * @return
     */
    public PaginationDto findByCreatorId(Integer pageNum, Integer pageSize, Long id) {
        //计算总记录数
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(id);
        int totalCount = (int) questionMapper.countByExample(questionExample);
        return getPageInfo(pageNum, pageSize, totalCount, id);
    }

    /**
     * 根据问题id查询 问题详情
     *
     * @param id
     * @return
     */
    public QuestionDto findById(Long id) {
        //查询问题信息
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null){
            //找不到问题，抛出异常及信息
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDto questionDto = new QuestionDto();
        //放入返回结果对象
        BeanUtils.copyProperties(question, questionDto);
        //通过创建人id查出用户
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        //放入返回结果
        questionDto.setUser(user);
        return questionDto;
    }

    /**
     * 根据id是否存在，创建或修改问题
     *
     * @param question
     */
    public void createOrUpdateQuestion(Question question) {
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insertSelective(question);
        } else {
            question.setGmtModified(System.currentTimeMillis());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(question, questionExample);
            if(updated != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    /**
     * 增加阅读数
     *
     * @param id
     */
    public void addViewCount(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.addViewCount(question);
    }

    /**
     * 将tags转为正则表达式后查询问题
     * @param queryQuestionDto
     * @return
     */
    public List<QuestionDto> findByTags(QuestionDto queryQuestionDto) {
        if(StringUtils.isBlank(queryQuestionDto.getTags())){
            return new ArrayList<>();
        }
        Question question = new Question();
        question.setId(queryQuestionDto.getId());
        //将，转为|
        String replaceTags = queryQuestionDto.getTags().replace("，", "|");
        question.setTags(replaceTags);
        //查出所有符合表达式的question
        List<Question> questionList = questionExtMapper.findByREGEXP(question);
        //赋值给questionDto
        return questionList.stream().map(q -> {
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(q, questionDto);
            return questionDto;
        }).collect(Collectors.toList());
    }
}

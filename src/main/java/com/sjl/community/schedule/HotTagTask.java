package com.sjl.community.schedule;

import com.sjl.community.cache.HotTagCache;
import com.sjl.community.mapper.QuestionMapper;
import com.sjl.community.model.Question;
import com.sjl.community.model.QuestionExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author song
 * @create 2020/3/5 20:17
 */
@Component
@Slf4j
public class HotTagTask {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private HotTagCache hotTagCache;

    @Scheduled(fixedRate = 1000 * 60 * 60 * 4) //四个小时
    public void hotTagSchedule(){
        log.info("开始时间：{}", new Date());
        int offset = 0;
        int limit = 5;
        List<Question> list = new ArrayList<>();
        Map<String, Integer> priorities = new HashMap<>();
        while(offset == 0 || list.size() == limit){
            list = questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(), new RowBounds(offset, limit));
            for (Question question : list) {
                log.info("question id : {}", question.getId());
                String[] splitTags = StringUtils.split(question.getTags(), ",");
                for (String tag : splitTags) {
                    if(priorities.get(tag) != null){
                        priorities.put(tag, priorities.get(tag) + 5 + question.getCommentCount());
                    }else{
                        priorities.put(tag, 5 + question.getCommentCount());
                    }
                }
            }
            offset += limit;
        }
        hotTagCache.updateHotTag(priorities);
        log.info("结束时间：{}", new Date());
    }
}

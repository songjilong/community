package com.sjl.community.cache;

import com.sjl.community.dto.HotTagDto;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author song
 * @create 2020/3/5 20:46
 */
@Component
@Data
public class HotTagCache {
    private List<String> topTags = new LinkedList<>();

    public void updateHotTag(Map<String, Integer> priorities) {
        int max = 10;
        PriorityQueue<HotTagDto> priorityQueue = new PriorityQueue<>(max);

        //将数据放入优先级队列
        priorities.forEach((name, priority) -> {
                HotTagDto hotTagDto = new HotTagDto();
                hotTagDto.setName(name);
                hotTagDto.setPriority(priority);
                if(priorityQueue.size() < max){
                    priorityQueue.add(hotTagDto);
                }else{
                    //每次将大的放进去
                    HotTagDto peek = priorityQueue.peek();
                    if(peek.compareTo(hotTagDto) < 0){
                        priorityQueue.poll();
                        priorityQueue.add(hotTagDto);
                    }
                }
            }
        );

        LinkedList<String> temp = new LinkedList<>();
        while(!priorityQueue.isEmpty()){
            temp.add(0, priorityQueue.poll().getName());
        }
        System.out.println("top 3 : " + temp);
        topTags = temp;
    }
}

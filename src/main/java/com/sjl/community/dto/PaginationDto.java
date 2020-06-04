package com.sjl.community.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author song
 * @create 2020/2/17 20:27
 */
@Data
public class PaginationDto<T> implements Serializable {
    private List<T> list;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private int prePage;
    private int nextPage;
    private int page;
    private int totalPage;
    /**
     * 当前分页结果的页码集
     */
    private List<Integer> pages = new ArrayList<>();

    public void setInfo(int totalCount, Integer pageNum, Integer pageSize) {
        //总页数
        if (totalCount % pageSize == 0) {
            totalPage = totalCount / pageSize;
        } else {
            totalPage = totalCount / pageSize + 1;
        }

        if (pageNum < 1){
            pageNum = 1;
        } else if (pageNum > totalPage){
            pageNum = totalPage;
        }

        page = pageNum;

        pages.add(pageNum);
        for (int i = 1; i <= 3; i++) {
            //左边的元素
            if (pageNum - i > 0) {
                pages.add(0, pageNum - i);
            }
            //右边的元素
            if (pageNum + i <= totalPage) {
                pages.add(pageNum + i);
            }
        }
        isFirstPage = pageNum == 1;
        isLastPage = pageNum == totalPage || totalCount == 0;
        hasPreviousPage = pageNum > 1;
        hasNextPage = pageNum < totalPage;
        prePage = pageNum > 1 ? pageNum - 1 : 1;
        nextPage = pageNum < totalPage ? pageNum + 1 : totalPage;
    }
}

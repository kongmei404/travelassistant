package com.travelassistant.param;

import lombok.Data;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/17 22:43 周一
 * description: 关键搜索参数
 */
@Data
public class ProductParamsSearch{

    private String search;
    private int    currentPage = 1;
    private int    pageSize = 15;

    /**
     * 运算分页起始值
     * @return
     */
    public int getFrom(){
        return (currentPage-1)*pageSize;
    }

    /**
     * 返回查询值
     * @return
     */
    public int getSize(){
        return pageSize;
    }
}

package com.travelassistant.product.param;

import lombok.Data;

import java.util.List;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/18 10:42 周二
 * description: 商品类别分组查询
 */
@Data
public class ProductParamInteger {

    private List<Integer> categoryID;
    private int currentPage = 1; //默认值
    private int pageSize = 15; //默认值

}

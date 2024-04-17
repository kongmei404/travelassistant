package com.travelassistant.param;

import lombok.Data;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/22 14:32 周六
 * description: 分页参数
 */
@Data
public class PageParam {

    private int    currentPage = 1;
    private int    pageSize = 15;
}

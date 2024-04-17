package com.travelassistant.service;

import com.travelassistant.param.PageParam;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/25 15:03 周二
 * description:
 */
public interface OrderService {

    /**
     * 分页查询订单数据
     * @param pageParam
     * @return
     */
    Object list(PageParam pageParam);
}

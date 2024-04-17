package com.travelassistant.order.service;

import com.travelassistant.param.OrderParam;
import com.travelassistant.param.PageParam;
import com.travelassistant.pojo.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/21  10:07 周五
 * description: 订单业务接口
 */
public interface OrderService extends IService<Order> {

    /**
     * 订单保存业务
     * @param orderParam
     * @return
     */
    Object save(OrderParam orderParam);

    /**
     * 订单数据查询业务
     * @param orderParam
     * @return
     */
    Object list(OrderParam orderParam);

    /**
     * 检查订单是否包含要删除的商品
     * @param productId
     * @return
     */
    Object check(Integer productId);

    /**
     * 分页查询订单数据
     * @param pageParam
     * @return
     */
    Object adminList(PageParam pageParam);
}

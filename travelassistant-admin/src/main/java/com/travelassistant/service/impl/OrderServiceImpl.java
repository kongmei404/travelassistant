package com.travelassistant.service.impl;

import com.travelassistant.clients.OrderClient;
import com.travelassistant.param.PageParam;
import com.travelassistant.service.OrderService;
import com.travelassistant.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/25 15:04 周二
 * description: 后台管理订单实现类
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderClient orderClient;

    /**
     * 分页查询订单数据
     *
     * @param pageParam
     * @return
     */
    @Override
    public Object list(PageParam pageParam) {

        R r = orderClient.adminList(pageParam);

        log.info("OrderServiceImpl.list业务结束，结果:{}",r);

        return r;
    }
}

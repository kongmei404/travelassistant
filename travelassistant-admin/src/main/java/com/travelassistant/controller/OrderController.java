package com.travelassistant.controller;

import com.travelassistant.param.PageParam;
import com.travelassistant.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/25 15:01 周二
 * description: 订单管理controller
 */
@RestController
@RequestMapping("order")
public class OrderController
{
    @Autowired
    private OrderService orderService;

    @GetMapping("list")
    public Object list(PageParam pageParam){

        return orderService.list(pageParam);
    }

}

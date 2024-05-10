package com.travelassistant.order.controller;

import com.travelassistant.order.service.OrderService;
import com.travelassistant.param.OrderParam;
import com.travelassistant.param.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/21 10:04 周五
 * description: 订单controller
 */

@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService orderService;

    /**
     * 订单数据保存
     * @param orderParam
     * @return
     */
    @PostMapping("save")
    public Object save(@RequestBody OrderParam orderParam){

        return orderService.save(orderParam);
    }


    /**
     * 订单集合查询,注意,按照类别查询!
     * @param orderParam
     * @return
     */
    @PostMapping("/list")
    public Object list(@RequestBody OrderParam orderParam){

        return orderService.list(orderParam);
    }

    @PostMapping("/noPayList")
    public Object noPayList(@RequestBody OrderParam orderParam){

        return orderService.noPayList(orderParam);
    }


    /**
     * 检查订单是否包含要删除的商品
     * @param productId
     * @return
     */
    @PostMapping("/check")
    public  Object check(@RequestBody Integer productId){
        return orderService.check(productId);
    }


    @PostMapping("/admin/list")
    public Object adminList(@RequestBody PageParam pageParam){

        return orderService.adminList(pageParam);
    }
}

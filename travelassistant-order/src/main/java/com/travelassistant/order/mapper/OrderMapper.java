package com.travelassistant.order.mapper;

import com.travelassistant.pojo.Order;
import com.travelassistant.vo.AdminOrderVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/21 10:08 周五
 * description: 订单mapper
 */
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 分页查询数据,返回order封装vo
     * @param offset
     * @param number
     * @return
     */
    List<AdminOrderVo> selectAdminOrders(@Param("offset") int offset, @Param("number")int number);
}

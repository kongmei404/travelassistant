package com.travelassistant.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/25 14:44 周二
 * description: 后台订单显示
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AdminOrderVo {

    @JsonProperty("order_id")
    private Long orderId;
    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("product_num")
    private Integer productNum; //商品种类
    @JsonProperty("order_num")
    private Integer orderNum; //订单中商品数量
    @JsonProperty("order_price")
    private Double  orderPrice; //订单金额
    @JsonProperty("order_time")
    private Long    orderTime; //订单时间
}

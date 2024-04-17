package com.travelassistant.vo;

import com.travelassistant.pojo.Order;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/21 9:51 周五
 * description: 订单vo对象
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class OrderVo extends Order {

    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("product_picture")
    private String productPicture;

}

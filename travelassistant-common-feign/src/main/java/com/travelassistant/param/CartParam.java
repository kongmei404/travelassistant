package com.travelassistant.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/20 22:57 周四
 * description: 购物车接收参数
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartParam {

    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("product_id")
    private Integer productId;
    private Integer num;
}

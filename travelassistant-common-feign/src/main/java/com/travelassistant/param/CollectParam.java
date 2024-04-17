package com.travelassistant.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/20 14:47 周四
 * description: 收藏模块接收参数
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CollectParam {

    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("product_id")
    private Integer productId;
}

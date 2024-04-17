package com.travelassistant.param;

import com.travelassistant.pojo.Address;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/21 16:49 周五
 * description: 地址收集信息
 */
@Data
public class AddressParam {

    @JsonProperty("user_id")
    private Integer userId;
    private Address add;
}

package com.travelassistant.param;

import lombok.Data;

import java.util.List;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/20 15:25 周四
 * description: 商品id集合
 */
@Data
public class ProductIdsParam {

    private List<Integer> productIds;
}

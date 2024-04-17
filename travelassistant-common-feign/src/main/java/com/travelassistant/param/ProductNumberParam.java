package com.travelassistant.param;

import lombok.Data;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/21 10:24 周五
 * description: 商品库存信息保存param
 */
@Data
public class ProductNumberParam {

    //商品id
    private Integer productId;
    //购买数量
    private Integer productNum;
}

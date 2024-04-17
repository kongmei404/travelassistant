package com.travelassistant.param;

import com.travelassistant.pojo.Product;
import lombok.Data;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/24 22:21 周一
 * description: 保存商品信息
 */
@Data
public class ProductSaveParam extends Product {

    //商品详情图片地址, 多图片地址使用 + 号拼接
    private String pictures;
}

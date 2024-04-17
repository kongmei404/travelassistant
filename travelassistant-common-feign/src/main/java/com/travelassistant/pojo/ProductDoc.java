package com.travelassistant.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/19 16:03 周三
 * description: 用于保存es文档数据,因为添加all字段,所以需要调整Product
 */
@Data
@NoArgsConstructor
public class ProductDoc extends Product {

    /**
     * 用于模糊查询字段,由商品名,标题和描述组成
     */
    private String all;

    public ProductDoc(Product product) {
        super(product.getProductId(),product.getProductName(),
                product.getCategoryId(),product.getProductTitle(),
                product.getProductIntro(),product.getProductPicture(),
                product.getProductPrice(),product.getProductSellingPrice(),
                product.getProductNum(),product.getProductSales());
        this.all = product.getProductName()+product.getProductTitle()+product.getProductIntro();
    }
}

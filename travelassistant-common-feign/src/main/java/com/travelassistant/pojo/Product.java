package com.travelassistant.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/17 22:11 周一
 * description: 商品pojo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("product")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product implements Serializable {

    public static final Long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @JsonProperty("product_id")
    private Integer productId;
    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("category_id")
    private String categoryId;
    /**
     * 手机title
     */
    @JsonProperty("product_title")
    private String productTitle;
    /**
     * 手机信息描述
     */
    @JsonProperty("product_intro")
    private String productIntro;
    @JsonProperty("product_picture")//这是商品图片的url
    private String productPicture;
    /**
     * 商品价格
     */
    @JsonProperty("product_price")
    private Double productPrice;
    /**
     * 售卖价格
     */
    @JsonProperty("product_selling_price")
    private Double productSellingPrice;
    /**
     * 商品库存
     */
    @JsonProperty("product_num")
    private int productNum;
    /**
     * 已卖数量
     */
    @JsonProperty("product_sales")
    private int productSales;


}

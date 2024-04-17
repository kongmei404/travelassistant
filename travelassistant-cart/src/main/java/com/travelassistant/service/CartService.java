package com.travelassistant.service;

import com.travelassistant.param.CartParam;
import com.travelassistant.pojo.Cart;
import com.travelassistant.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/20 21:59 周四
 * description:
 */
public interface CartService  extends IService<Cart> {

    /**
     * 添加购物车
     * @param cartParam
     * @return
     */
    R save(CartParam cartParam);

    /**
     * 查询购物车数据集合
     * @param cartParam
     * @return
     */
    R list(CartParam cartParam);

    /**
     * 修改购物车数量
     * @param cartParam
     * @return
     */
    R update(CartParam cartParam);

    /**
     * 移除购物车数据
     * @param cartParam
     * @return
     */
    R remove(CartParam cartParam);

    /**
     * 检查商品是否存在
     * @param productId
     * @return
     */
    R check(Integer productId);
}

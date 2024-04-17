package com.travelassistant.controller;

import com.travelassistant.service.CartService;
import com.travelassistant.param.CartParam;
import com.travelassistant.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/20 21:57 周四
 * description: 购物车controller
 */
@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("save")
    public R save(@RequestBody CartParam cartParam){

        return cartService.save(cartParam);
    }

    @PostMapping("list")
    public R list(@RequestBody CartParam cartParam){

        return cartService.list(cartParam);
    }


    @PostMapping("update")
    public R update(@RequestBody CartParam cartParam){

        return cartService.update(cartParam);
    }


    @PostMapping("remove")
    public R remove(@RequestBody CartParam cartParam){

        return cartService.remove(cartParam);
    }


    /**
     * 检查是否存在对应商品
     * @param productId
     * @return
     */
    @PostMapping("check")
    public R check(@RequestBody Integer productId){

        return cartService.check(productId);
    }

}

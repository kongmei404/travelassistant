package com.travelassistant.clients;

import com.travelassistant.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/25 9:23 周二
 * description: 购物车feign客户端
 */
@FeignClient(value = "cart-service")
public interface CartClient {

    /**
     * 检查商品有没有被引用,有取消删除!
     * @param productId
     * @return
     */
    @PostMapping("/cart/check")
    R checkProduct(Integer productId);
}

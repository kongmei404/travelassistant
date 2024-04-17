package com.travelassistant.clients;

import com.travelassistant.param.PageParam;
import com.travelassistant.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/25 9:28 周二
 * description: OrderClient
 */
@FeignClient(value = "order-service")
public interface OrderClient {


    /**
     * 检查商品有没有被引用,有取消删除!
     * @param productId
     * @return
     */
    @PostMapping("/order/check")
    R checkProduct(Integer productId);


    @PostMapping("order/admin/list")
    R adminList(@RequestBody PageParam pageParam);

}

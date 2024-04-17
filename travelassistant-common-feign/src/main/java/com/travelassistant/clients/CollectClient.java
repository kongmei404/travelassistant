package com.travelassistant.clients;

import com.travelassistant.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/25 9:29 周二
 * description: 收藏client
 */
@FeignClient(value = "collect-service")
public interface CollectClient {

    @PostMapping("/collect/remove/bypid")
    R removeByPID(@RequestBody Integer productId);
}

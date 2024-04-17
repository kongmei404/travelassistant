package com.travelassistant.clients;

import com.travelassistant.param.PageParam;
import com.travelassistant.pojo.User;
import com.travelassistant.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/17 22:24 周一
 * description: userfeign调用标准化接口
 */
@FeignClient(value = "user-service")
public interface UserClient {

    /**
     * 后台管理,展示用户信息接口
     * @param pageParam
     * @return
     */
    @PostMapping("/user/list")
    R listPage(@RequestBody PageParam pageParam);

    @PostMapping("/user/remove")
    R remove(@RequestBody Integer userId);

    @PostMapping("/user/update")
    R update(@RequestBody User user);

    @PostMapping("/user/register")
    R save(@RequestBody User user);
}

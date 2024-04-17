package com.travelassistant.clients;

import com.travelassistant.param.ProductParamsSearch;
import com.travelassistant.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/19 19:18 周三
 * description:
 */
@FeignClient(name = "search-service")
public interface SearchClient {

    /**
     * 搜索服务 商品查询
     * @param productParamsSearch
     * @return
     */
    @PostMapping("/search/product")
    R search(@RequestBody ProductParamsSearch productParamsSearch);

}

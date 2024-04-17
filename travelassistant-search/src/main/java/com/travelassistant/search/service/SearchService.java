package com.travelassistant.search.service;

import com.travelassistant.param.ProductParamsSearch;
import com.travelassistant.utils.R;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/19 18:55 周三
 * description:
 */
public interface SearchService {

    /**
     * 商品搜索
     * @param productParamsSearch
     * @return
     */
    R search(ProductParamsSearch productParamsSearch) throws JsonProcessingException;
}

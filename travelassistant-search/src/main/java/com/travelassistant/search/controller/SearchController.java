package com.travelassistant.search.controller;

import com.travelassistant.param.ProductParamsSearch;
import com.travelassistant.search.service.SearchService;
import com.travelassistant.utils.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/19 18:47 周三
 * description: 搜索服务controller
 */
@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("product")
    public R productList(@RequestBody ProductParamsSearch productParamsSearch) throws JsonProcessingException {


        return searchService.search(productParamsSearch);
    }

}

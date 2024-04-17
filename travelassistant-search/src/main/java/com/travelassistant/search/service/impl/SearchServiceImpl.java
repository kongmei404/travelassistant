package com.travelassistant.search.service.impl;

import com.travelassistant.param.ProductParamsSearch;
import com.travelassistant.pojo.Product;
import com.travelassistant.search.service.SearchService;
import com.travelassistant.utils.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/19 18:55 周三
 * description:
 */
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Autowired
    private RestHighLevelClient client;

    /**
     * 商品搜索
     * @param productParamsSearch
     * @return
     */
    @Override
    public R search(ProductParamsSearch productParamsSearch) throws JsonProcessingException {

        SearchRequest searchRequest = new SearchRequest("product");

        if (StringUtils.isEmpty(productParamsSearch.getSearch())){
            //如果为null,查询全部
            searchRequest.source().query(QueryBuilders.matchAllQuery());
        }else{
            //不为空 all字段进行搜索
            searchRequest.source().query(QueryBuilders.matchQuery("all",productParamsSearch.getSearch()));
        }

        //设置分页参数
        searchRequest.source().from(productParamsSearch.getFrom());
        searchRequest.source().size(productParamsSearch.getSize());

        SearchResponse response = null;
        try {
            response = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw  new RuntimeException(e);
        }

        //结果集解析
        //获取集中的结果
        SearchHits hits = response.getHits();
        //获取符合的数量
        long total = hits.getTotalHits().value;

        SearchHit[] items = hits.getHits();

        List<Product> productList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (SearchHit item : items) {
            //获取单挑json数据
            String json = item.getSourceAsString();
            Product product = objectMapper.readValue(json, Product.class);
            productList.add(product);
        }

        return R.ok(null,productList,total);
    }


}

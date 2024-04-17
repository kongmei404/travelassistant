package com.travelassistant.clients;

import com.travelassistant.param.ProductIdsParam;
import com.travelassistant.param.ProductParamsSearch;
import com.travelassistant.param.ProductSaveParam;
import com.travelassistant.pojo.Product;
import com.travelassistant.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/19 16:01 周三
 * description:商品客户端
 */
@FeignClient(value = "product-service")//将服务提供者的服务名称写到这里
public interface ProductClient {

    /**
     * 商品全部数据调用
     * @return
     */
    @GetMapping("/product/list")
    List<Product> list();

    /**
     * 收藏模块调用
     * @param productIdsParam
     * @return
     */
    @PostMapping("/product/ids")
    List<Product> ids(@RequestBody ProductIdsParam productIdsParam);

    @PostMapping("/product/category/count")
    long count(@RequestBody  Integer categoryId);


    /**
     * 后台管理调用!
     * @param paramsSearch
     * @return
     */
    @PostMapping("/product/search")
    R searchPage(@RequestBody ProductParamsSearch paramsSearch);

    @PostMapping("/product/save")
    R save(@RequestBody ProductSaveParam saveParam);

    @PostMapping("/product/update")
    R update(@RequestBody  Product product);

    @PostMapping("product/remove")
    R remove(@RequestBody Integer productId);
}

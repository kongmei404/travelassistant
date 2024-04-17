package com.travelassistant.service.impl;

import com.travelassistant.clients.ProductClient;
import com.travelassistant.param.ProductParamsSearch;
import com.travelassistant.param.ProductSaveParam;
import com.travelassistant.pojo.Product;
import com.travelassistant.service.ProductService;
import com.travelassistant.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/24 15:52 周一
 * description: 商品服务实现类
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductClient productClient;

    /**
     * 商品分页,关键字分页查询!
     * @param productParamsSearch
     * @return
     */
    @Override
    public Object list(ProductParamsSearch productParamsSearch) {

        R r = productClient.searchPage(productParamsSearch);

        log.info("ProductServiceImpl.list业务结束，结果:{}",r);
        return r;
    }

    /**
     * 保存商品业务!
     * 1.保存商品
     * 2.保存商品图片
     * 3.商品缓存数据处理 [注解]
     * 4.查询缓存es处理 [异步]
     *
     * @param saveParam
     * @return
     */
    @CacheEvict(value = "list.product", allEntries = true)//清空缓存
    @Override//这个注解是用来更新缓存的
    public Object save(ProductSaveParam saveParam) {

        //保存 商品和商品图片
        R r = productClient.save(saveParam);
        log.info("ProductServiceImpl.save业务结束，结果:{}",r);
        return r;
    }

    /**
     * 修改商品信息
     * 1.修改商品信息
     * 2.清空商品缓存集合
     * 3.更新缓存es处理 [异步]
     *
     * @param product
     * @return
     */
    @CacheEvict(value = "list.product",allEntries = true)
    @CachePut(value = "product",key = "#product.productId")
    @Override
    public Object update(Product product) {

        R r = productClient.update(product);
        log.info("ProductServiceImpl.update业务结束，结果:{}",r);
        return r;
    }

    /**
     * 删除商品数据
     *    清空缓存
     *    调用商品服务
     * @param productId
     * @return
     */
    @Caching(
        evict = {
           @CacheEvict(value = "list.product",allEntries = true),
           @CacheEvict(value = "product",key = "#productId")
        }
    )
    @Override
    public Object remove(Integer productId) {

        R r = productClient.remove(productId);
        log.info("ProductServiceImpl.remove业务结束，结果:{}",r);
        return r;
    }
}

package com.travelassistant.service.impl;

import com.travelassistant.clients.CategoryClient;
import com.travelassistant.param.PageParam;
import com.travelassistant.pojo.Category;
import com.travelassistant.service.CategoryService;
import com.travelassistant.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/24 10:44 周一
 * description: 类别业务服务
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryClient categoryClient;

    /**
     * 分页数据查询
     * @param pageParam
     * @return
     */
    @Cacheable(value="list.category",key = "#pageParam.currentPage+'-'+#pageParam.pageSize")
    @Override
    public Object listPage(PageParam pageParam) {

        R r = categoryClient.pageList(pageParam);

        log.info("CategoryServiceImpl.listPage业务结束，结果:{}",r);
        return r;
    }

    /**
     * 类别数据修改
     *
     * @param category
     * @return
     */
    @Caching(
            evict = {
                    @CacheEvict(value="list.category",allEntries = true),
                    @CacheEvict(value = "category",allEntries = true)
            }
    )
    @Override
    public Object update(Category category) {

        R r = categoryClient.update(category);
        log.info("CategoryServiceImpl.update业务结束，结果:{}",r);

        return r;
    }

    /**
     * 移除类别数据
     * @param categoryId
     * @return
     */
    @Caching(
            evict = {
                    @CacheEvict(value="list.category",allEntries = true),
                    @CacheEvict(value = "category",key = "#categoryId")
            }
    )
    @Override
    public Object remove(Integer categoryId) {

        R r = categoryClient.remove(categoryId);
        log.info("CategoryServiceImpl.remove业务结束，结果:{}",r);
        return r;
    }

    /**
     * 类别数据保存
     *
     * @param category
     * @return
     */
    @CacheEvict(value = "list.category",allEntries = true)
    @Override
    public Object save(Category category) {

        //类别数据保存
        R r = categoryClient.save(category);

        log.info("CategoryServiceImpl.save业务结束，结果:{}",r);

        return r;
    }
}

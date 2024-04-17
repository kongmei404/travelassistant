package com.travelassistant.service;

import com.travelassistant.param.PageParam;
import com.travelassistant.pojo.Category;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/24 10:43 周一
 * description:
 */
public interface CategoryService {

    /**
     * 分页数据查询
     * @param pageParam
     * @return
     */
    Object listPage(PageParam pageParam);

    /**
     * 类别数据修改
     * @param category
     * @return
     */
    Object update(Category category);

    /**
     * 移除类别数据
     * @param categoryId
     * @return
     */
    Object remove(Integer categoryId);

    /**
     * 类别数据保存
     * @param category
     * @return
     */
    Object save(Category category);
}

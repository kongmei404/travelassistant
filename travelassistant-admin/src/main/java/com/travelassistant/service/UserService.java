package com.travelassistant.service;

import com.travelassistant.param.PageParam;
import com.travelassistant.pojo.User;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/22 14:33 周六
 * description:
 */
public interface UserService {

    /**
     * 分页数据查询,用户模块
     * @param pageParam
     * @return
     */
    Object listPage(PageParam pageParam);

    /**
     * 删除用户数据
     * @param userId
     * @return
     */
    Object remove(Integer userId);

    /**
     * 修改用户数据
     * @param user
     * @return
     */
    Object update(User user);

    /**
     * 保存用户数据,用户注册功能
     * @param user
     * @return
     */
    Object save(User user);
}

package com.travelassistant.user.service;

import com.travelassistant.param.PageParam;
import com.travelassistant.pojo.User;
import com.travelassistant.utils.R;

import java.util.List;

/**
 * projectName: b2c_cloud_store
 *
 * @author: 邱绍峰
 * time: 2024/03/16 21:30 周日
 * description:
 */
public interface UserService{

    List<User> list();

    /**
     * 检查账号是否可用
     * @param userName
     * @return
     */
    R check(String userName);

    /**
     * 进行账号注册
     * @param user 参数没有校验
     * @return
     */
    R register(User user);

    /**
     * 进行账号登录
     * @param user
     * @return
     */
    R login(User user);

    /**
     * 分页数据查询
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
     * 修改用户密码
     * @param user
     * @return
     */
    Object update(User user);
}

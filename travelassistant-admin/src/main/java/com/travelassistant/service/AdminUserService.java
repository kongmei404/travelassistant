package com.travelassistant.service;

import com.travelassistant.param.AdminUserParam;
import com.travelassistant.pojo.AdminUser;
import com.travelassistant.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/22 10:38 周六
 * description: 后台管理登录响应功能
 */
public interface AdminUserService extends IService<AdminUser> {

    /**
     * 后台管理登录页面
     * @param adminUserParam
     * @return
     */
    R login(AdminUserParam adminUserParam);
}

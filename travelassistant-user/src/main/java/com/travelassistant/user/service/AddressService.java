package com.travelassistant.user.service;

import com.travelassistant.param.AddressParam;
import com.travelassistant.utils.R;

/**
 * projectName: b2c_cloud_store
 *
 * @author: 邱绍峰
 * time: 2024/03/17 11:15 周一
 * description:
 */
public interface AddressService {

    /**
     * 查询地址列表
     * @param userId
     * @return
     */
    R list(Integer userId);

    /**
     * 保存数据库数据
     * @param address
     * @return
     */
    R save(AddressParam address);

    /**
     * 删除地址数据
     * @param id
     * @return
     */
    R remove(Integer id);
}

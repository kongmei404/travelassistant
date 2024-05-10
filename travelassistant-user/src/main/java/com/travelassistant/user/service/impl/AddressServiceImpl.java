package com.travelassistant.user.service.impl;

import com.travelassistant.param.AddressParam;
import com.travelassistant.pojo.Address;
import com.travelassistant.user.mapper.AddressMapper;
import com.travelassistant.user.service.AddressService;
import com.travelassistant.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/17 11:19 周一
 * description:
 */
@Slf4j
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    /**
     * 查询地址列表
     * @param userId
     * @return
     */
    @Override
    public R list(Integer userId) {

        //1.参数处理
        if (userId == null){
            //此处,抛出异常,后续统一异常收集处理!
            log.info("AddressServiceImpl.list业务开始，参数:{}",userId);
            return R.fail("展示业务失败!");
        }
        //2.数据库查询
        QueryWrapper<Address> queryWrapper
                = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<Address> addressList = addressMapper.selectList(queryWrapper);
        //3.结果处理
        R ok = R.ok(addressList);

        log.info("AddressServiceImpl.list业务结束，结果:{}",ok);

        return ok;
    }

    /**
     * 保存数据库数据
     *
     * @param addressParam
     * @return
     */
    @Override
    public R save(AddressParam addressParam) {


        Address address = addressParam.getAdd();
        address.setUserId(addressParam.getUserId());

        //1.数据库插入
        int rows = addressMapper.insert(address);

        //2.返回结果处理
        if (rows == 0){
            return R.fail("地址保存失败!");
        }

        log.info("AddressServiceImpl.save业务结束，结果:{}",address);
        //调用查询,返回全部数据!
        return list(address.getUserId());
    }

    /**
     * 删除地址数据
     *
     * @param id
     * @return
     */
    @Override
    public R remove(Integer id) {

        if (id == null){
           log.info("AddressServiceImpl.remove业务开始，参数:{}",id);
           return R.fail("地址移除失败!");
        }

        int rows = addressMapper.deleteById(id);


        log.info("AddressServiceImpl.remove业务结束，移除结果:{}",rows);

        if (rows == 0){

            return R.fail("地址移除失败!");
        }

        return R.ok("地址移除成功!");
    }
}

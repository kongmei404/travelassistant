package com.travelassistant.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * projectName: b2c_cloud_store
 *
 * @author: 邱绍峰
 * time: 2024/03/17 11:12 周一
 * description: 地址pojo
 */
@Data
@TableName("address")
public class Address {

    @TableId(type = IdType.AUTO)
    private Integer id;


    private String address;
    private String linkman;
    private String phone;
    @TableField("user_id")
    private Integer userId;

}

package com.travelassistant.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/22 10:01 周六
 * description: 后台管理用户
 */
@Data
@TableName("admin_user")
public class AdminUser  implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer userId;
    private String userName;
    private String userAccount;
    private String userPassword;
    private String userPhone;
    private Date   createTime;
    private Integer userRole;

}

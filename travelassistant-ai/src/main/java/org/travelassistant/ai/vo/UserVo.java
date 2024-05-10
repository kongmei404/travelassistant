package org.travelassistant.ai.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserVo {

    private Integer userId;
    private String  userName;
    private String  password;
    private String  userPhonenumber;

}

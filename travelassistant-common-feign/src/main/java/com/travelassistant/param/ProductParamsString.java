package com.travelassistant.param;

import lombok.Data;

import java.util.List;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/17 22:43 周一
 * description: 类别名称集合参数
 * todo: 对应参数类型 {categoryName:["","",""]}
 */
@Data
public class ProductParamsString {

    private List<String> categoryName;
}

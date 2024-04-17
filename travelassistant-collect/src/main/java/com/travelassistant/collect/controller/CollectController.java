package com.travelassistant.collect.controller;

import com.travelassistant.collect.service.CollectService;
import com.travelassistant.param.CollectParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/20 14:49 周四
 * description: 收藏controller
 */
@RestController
@RequestMapping("collect")
public class CollectController {

    @Autowired
    private CollectService collectService;


    @PostMapping("save")
    public Object save(@RequestBody CollectParam collectParam){

        return collectService.save(collectParam);
    }


    @PostMapping("list")
    public Object list(@RequestBody CollectParam collectParam){

        return collectService.list(collectParam);
    }

    @PostMapping("remove")
    public Object remove(@RequestBody CollectParam collectParam){

        return collectService.remove(collectParam);
    }


    /**
     * 根据商品id删除
     * @param productId
     * @return
     */
    @PostMapping("remove/bypid")
    public Object removeByPid(@RequestBody Integer productId){

        return collectService.removeByPid(productId);
    }


}

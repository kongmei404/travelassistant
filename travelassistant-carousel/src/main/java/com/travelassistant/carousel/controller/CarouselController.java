package com.travelassistant.carousel.controller;

import com.travelassistant.carousel.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/17 17:42 周一
 * description:
 */

@RestController
@RequestMapping("carousel")
public class CarouselController  {

    @Autowired
    private CarouselService carouselService;

    /**
     * 查询首页数据,查询优先级最高的四条
     * @return
     */
    @PostMapping("list")
    public Object list(){

        return  carouselService.list();//查询首页数据
    }

}

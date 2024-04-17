package com.travelassistant.carousel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/17 16:56 周一
 * description: 启动类
 */
@SpringBootApplication
@MapperScan(basePackages = "com.travelassistant.carousel.mapper")
@EnableCaching
public class CarouselApplication {


    public static void main(String[] args) {
        SpringApplication.run(CarouselApplication.class,args);
    }

}

package com.travelassistant.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * projectName: b2c_cloud_store
 *
 * @author: 邱绍峰
 * time: 2024/03/16 21:21 周日
 * description: 启动类
 */
@MapperScan(basePackages = "com.travelassistant.user.mapper")
@SpringBootApplication
//开启feign的客户端,暂时不需要
@EnableFeignClients()
public class FrontUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrontUserApplication.class,args);
    }

}

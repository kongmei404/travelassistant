package com.travelassistant;

import com.travelassistant.clients.CategoryClient;
import com.travelassistant.clients.OrderClient;
import com.travelassistant.clients.ProductClient;
import com.travelassistant.clients.UserClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/22 9:38 周六
 * description: 启动类
 */
@MapperScan("com.travelassistant.mapper")
@SpringBootApplication
@EnableFeignClients(clients = {UserClient.class, CategoryClient.class, ProductClient.class, OrderClient.class})  //添加客户端引用
@EnableCaching //开启缓存支持
public class AdminApplication  {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class,args);
    }
}

package com.travelassistant.order;

import com.travelassistant.clients.ProductClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/21 9:45 周五
 * description: 订单启动类
 */
@MapperScan(basePackages = "com.travelassistant.order.mapper")
@SpringBootApplication
@EnableFeignClients(clients = {ProductClient.class})
@EnableCaching
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}

package com.travelassistant.collect;

import com.travelassistant.clients.ProductClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/20 14:27 周四
 * description: 启动类
 */
@MapperScan(basePackages = "com.travelassistant.collect.mapper")
@SpringBootApplication
@EnableFeignClients(clients = {ProductClient.class})
public class CollectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollectApplication.class,args);
    }
}

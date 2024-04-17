package com.travelassistant;

import com.travelassistant.clients.ProductClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/17 21:28 周一
 * description: 启动类
 */
@SpringBootApplication
@MapperScan(basePackages = "com.travelassistant.mapper")
@EnableFeignClients(clients = {ProductClient.class})
public class CategoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(CategoryApplication.class,args);
    }

}

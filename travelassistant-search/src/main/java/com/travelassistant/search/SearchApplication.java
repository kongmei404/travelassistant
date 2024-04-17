package com.travelassistant.search;

import com.travelassistant.clients.ProductClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/19 15:05 周三
 * description: 启动类
 */
//排除自动导入数据库配置,否者出现为配置连接池信息异常
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableFeignClients(clients = ProductClient.class)
public class SearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class,args);
    }
}

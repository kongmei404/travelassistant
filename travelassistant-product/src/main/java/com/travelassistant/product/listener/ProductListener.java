package com.travelassistant.product.listener;

import com.travelassistant.param.ProductNumberParam;
import com.travelassistant.product.service.ProductService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/21 11:07 周五
 * description: product模块 rbmq监听器
 */

@Component
public class ProductListener {

    @Autowired
    private ProductService productService;

    /**
     * 修改库存数据
     * @param productNumberParams
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "sub.queue"),
            exchange = @Exchange("topic.ex"),
            key = "sub.number"
    ))
    public void subNumber(List<ProductNumberParam> productNumberParams){
        System.err.println("ProductListener.subNumber");
        System.err.println("productNumberParams = " + productNumberParams);

        //调用业务修改库存即可
        productService.batchNumber(productNumberParams);
    }

}

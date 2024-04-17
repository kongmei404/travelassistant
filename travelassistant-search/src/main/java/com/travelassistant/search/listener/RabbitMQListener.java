package com.travelassistant.search.listener;

import com.travelassistant.pojo.Product;
import com.travelassistant.pojo.ProductDoc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/19 20:28 周三
 * description:
 */
@Component
public class RabbitMQListener {


    @Autowired
    private RestHighLevelClient client;

    /**
     * 更新和插入数据同一个!
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "insert.queue"),
            exchange = @Exchange("topic.ex"),
            key = "insert.product"
    ))
    public void insert(Product product) throws IOException {

        IndexRequest indexRequest = new IndexRequest("product")
                .id(product.getProductId().toString());

        ProductDoc productDoc = new ProductDoc(product);

        ObjectMapper objectMapper = new ObjectMapper();
        String json  = objectMapper.writeValueAsString(productDoc);

        indexRequest.source(json, XContentType.JSON);

        client.index(indexRequest, RequestOptions.DEFAULT);

    }


    /**
     * 删除数据
     * @param productId
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "delete.queue"),
            exchange = @Exchange("topic.ex"),
            key = "delete.product"
    ))
    public void remove(Integer productId) throws IOException {

        System.out.println("RabbitMQListener.remove");
        System.out.println("productId = " + productId);
        DeleteRequest request = new DeleteRequest("product")
                .id(productId.toString());

        client.delete(request,RequestOptions.DEFAULT);
    }

}

package com.travelassistant.order.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelassistant.clients.ProductClient;
import com.travelassistant.order.controller.AliPayController;
import com.travelassistant.order.mapper.OrderMapper;
import com.travelassistant.order.pojo.AliPay;
import com.travelassistant.order.service.OrderService;
import com.travelassistant.param.OrderParam;
import com.travelassistant.param.PageParam;
import com.travelassistant.param.ProductIdsParam;
import com.travelassistant.param.ProductNumberParam;
import com.travelassistant.pojo.Order;
import com.travelassistant.pojo.Product;
import com.travelassistant.utils.R;
import com.travelassistant.vo.AdminOrderVo;
import com.travelassistant.vo.CartVo;
import com.travelassistant.vo.OrderVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/21 10:07 周五
 * description: 订单业务实现
 */
@Slf4j
@Service
public class OrderServiceImpl  extends ServiceImpl<OrderMapper,Order> implements OrderService {

    @Autowired
    private ProductClient productClient;

    /**
     * 消息队列发送
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    AliPayController aliPayController;



    /**
     * 订单保存业务
     * 库存和购物车使用mq异步,避免分布式事务!
     * @param orderParam
     * @return
     */
//    @Transactional //添加事务
    @Override
    public Object save(OrderParam orderParam) {

        ObjectMapper objectMapper = new ObjectMapper();

        Jedis resource = jedisPool.getResource();
        //修改清空购物车的参数
        List<Integer> cartIds = new ArrayList<>();
        //修改批量插入数据库的参数
        List<Order>  orderList = new ArrayList<>();
        //商品修改库存参数集合
        List<ProductNumberParam>  productNumberParamList  = new ArrayList<>();

        Integer userId = orderParam.getUserId();
        List<CartVo> products = orderParam.getProducts();
        //封装order实体类集合
        //统一生成订单编号和创建时间
        //使用时间戳 + 做订单编号和事件
        long ctime = System.currentTimeMillis();


        //遍历 购物车
        for (CartVo cartVo : products) {
            cartIds.add(cartVo.getId()); //进行购物车订单保存
            //订单信息保存
            Order order = new Order();
            order.setOrderId(ctime);
            order.setUserId(userId);
            order.setOrderTime(ctime);
            order.setProductId(cartVo.getProductID());
            order.setProductNum(cartVo.getNum());
            order.setProductPrice(cartVo.getPrice());
            orderList.add(order); //添加用户信息

            //修改信息存储
            ProductNumberParam productNumberParam = new ProductNumberParam();
            productNumberParam.setProductId(cartVo.getProductID());
            productNumberParam.setProductNum(cartVo.getNum());
            productNumberParamList.add(productNumberParam); //添加集合

        }

        //保存预订单信息到redis
        resource.lpush(userId + "orderID",orderList.stream().map(order -> {
            try {
                return objectMapper.writeValueAsString(order);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).toArray(String[]::new));//存储订单信息
        resource.expire(userId + "orderID", 60 * 15);//设置过期时间
        resource.close();//关闭连接



//        /**
//         *  交换机: topic.ex
//         *  routingkey: sub.number
//         *  消息: 商品id和减库存数据集合
//         */
//        rabbitTemplate.convertAndSend("topic.ex","sub.number",productNumberParamList);

        //清空对应购物车数据即可 [注意: 不是清空用户所有的购物车数据] [cart-service] [异步通知]
        /**
         * 交换机:topic.ex
         * routingkey: clear.cart
         * 消息: 要清空的购物车id集合
         */
        rabbitTemplate.convertAndSend("topic.ex","clear.cart",cartIds);



        R ok = R.ok("订单生成成功!");
        log.info("OrderServiceImpl.save业务结束，结果:{}",ok);
        return ok;
    }

    /**
     * 订单数据查询业务
     *
     * @param orderParam
     * @return
     */
    @Cacheable(value = "order",key = "#orderParam.userId")//缓存,
    @Override
    public Object list(OrderParam orderParam) {

        Integer userId = orderParam.getUserId();
        //查询用户对应的全部订单数据
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("user_id",userId);//
        List<Order> orderList = this.list(orderQueryWrapper);

        List<List<OrderVo>> result = getLists(orderList);

        R ok = R.ok(result);
        log.info("OrderServiceImpl.list业务结束，结果:{}",ok);
        return ok;
    }

    @Override
    public Object noPayList(OrderParam orderParam) {
        Integer userId = orderParam.getUserId();//用户id
        ObjectMapper objectMapper = new ObjectMapper();//json转换工具
        Jedis resource = jedisPool.getResource();//获取连接
        List<String> lrange = resource.lrange(userId + "orderID", 0, -1);//获取全部数据
        resource.close();//关闭连接
        if (lrange.isEmpty()) {
            return R.fail("没有未付款的订单!");
        }
        List<Order> orderList = lrange.stream().map(s -> {
            try {
                return objectMapper.readValue(s, Order.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

        List<List<OrderVo>> result = getLists(orderList);
        return R.ok(result);
    }


    /**
     * 将订单数据按订单分组
     * @param orderList
     * @return
     */
    @NotNull
    private List<List<OrderVo>> getLists(List<Order> orderList) {
        Set<Integer> productIds = new HashSet<>();
        for (Order order : orderList) {
            productIds.add(order.getProductId());
        }


        //数据按订单分组
        Map<Long, List<Order>> listMap = orderList.stream().
                collect(Collectors.groupingBy(Order::getOrderId));

        //结果集封装,返回即可
        ProductIdsParam productIdsParam = new ProductIdsParam();
        productIdsParam.setProductIds(new ArrayList<>(productIds));

        List<Product> productList = productClient.ids(productIdsParam);
        //商品数据
        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getProductId, v -> v));

        //结果封装
        List<List<OrderVo>> result = new ArrayList<>();

        for (List<Order> orders : listMap.values()) {
            List<OrderVo> orderVos = new ArrayList<>();
            for (Order order : orders) {
                //返回vo数据封装
                OrderVo orderVo = new OrderVo();
                Product product = productMap.get(order.getProductId());
                orderVo.setProductName(product.getProductName());
                orderVo.setProductPicture(product.getProductPicture());
                orderVo.setId(order.getId());
                orderVo.setOrderId(order.getOrderId());
                orderVo.setOrderTime(order.getOrderTime());
                orderVo.setProductNum(order.getProductNum());
                orderVo.setProductId(order.getProductId());
                orderVo.setProductPrice(order.getProductPrice());
                orderVo.setUserId(order.getUserId());
                orderVos.add(orderVo);
            }
            result.add(orderVos);
        }
        return result;
    }

    /**
     * 检查订单是否包含要删除的商品
     *
     * @param productId
     * @return
     */
    @Override
    public Object check(Integer productId) {

        QueryWrapper<Order> queryWrapper
                  = new QueryWrapper<>();

        queryWrapper.eq("product_id",productId);

        Long total = baseMapper.selectCount(queryWrapper);

        if (total == 0){

            return R.ok("订单中不存在要删除的商品!");
        }

        return R.fail("订单中存在要删除的商品,删除失败!");
    }

    /**
     * 分页查询订单数据
     *
     * @param pageParam
     * @return
     */
    @Override
    public Object adminList(PageParam pageParam) {

        int offset = (pageParam.getCurrentPage()-1)*pageParam.getPageSize();
        int number = pageParam.getPageSize();

        //查询数量
        Long total = orderMapper.selectCount(null);
        //自定义查询
        List<AdminOrderVo> adminOrderVoList = orderMapper.selectAdminOrders(offset,number);


        return R.ok("查询成功",adminOrderVoList,total);
    }


}

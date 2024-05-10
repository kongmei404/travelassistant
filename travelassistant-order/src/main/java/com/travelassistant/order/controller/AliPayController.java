package com.travelassistant.order.controller;
 
import com.alipay.easysdk.factory.Factory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelassistant.order.config.AliPayConfig;
import com.travelassistant.order.pojo.AliPay;
import com.travelassistant.order.service.OrderService;
import com.travelassistant.order.service.impl.OrderServiceImpl;
import com.travelassistant.param.ProductNumberParam;
import com.travelassistant.pojo.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author 
 * @Date Created in  2023/5/5 15:23
 * @DESCRIPTION:
 * @Version V1.0
 */
@RestController
@RequestMapping("alipay")
@Transactional(rollbackFor = Exception.class)
public class AliPayController {
 
   @Resource
   AliPayConfig aliPayConfig;

   @Autowired
   private OrderService orderService;

   @Autowired
   private JedisPool jedisPool;

   @Autowired
   private OrderServiceImpl orderServiceImpl;

   /**
    * 消息队列发送
    */
   @Autowired
   private RabbitTemplate rabbitTemplate;

 
   private static final String GATEWAY_URL ="https://openapi-sandbox.dl.alipaydev.com/gateway.do";
   private static final String FORMAT ="JSON";
   private static final String CHARSET ="utf-8";
   private static final String SIGN_TYPE ="RSA2";
 
   @PostMapping("/pay") // 前端路径参数格式?subject=xxx&traceNo=xxx&totalAmount=xxx
   public void pay(@RequestBody AliPay aliPay, HttpServletResponse httpResponse) throws Exception {
      AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, aliPayConfig.getAppId(),
              aliPayConfig.getAppPrivateKey(), FORMAT, CHARSET, aliPayConfig.getAlipayPublicKey(), SIGN_TYPE);
      AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
      request.setNotifyUrl(aliPayConfig.getNotifyUrl());
      request.setReturnUrl(aliPayConfig.getReturnUrl());
      request.setBizContent("{\"out_trade_no\":\"" + aliPay.getTraceNo() + "\","
              + "\"total_amount\":\"" + aliPay.getTotalAmount() + "\","
              + "\"subject\":\"" + aliPay.getSubject() + "\","
              + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
      String form = "";
      try {
         // 调用SDK生成表单
         form = alipayClient.pageExecute(request).getBody();
      } catch (AlipayApiException e) {
         e.printStackTrace();
      }
      httpResponse.setContentType("text/html;charset=" + CHARSET);
      // 直接将完整的表单html输出到页面
      httpResponse.getWriter().write(form);
      httpResponse.getWriter().flush();
      httpResponse.getWriter().close();
   }
 
   @PostMapping("/notify")  // 注意这里必须是POST接口
   public String payNotify(HttpServletRequest request) throws Exception {
      if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
         System.out.println("=========支付宝异步回调========");

         Map<String, String> params = new HashMap<>();
         Map<String, String[]> requestParams = request.getParameterMap();
         for (String name : requestParams.keySet()) {
            params.put(name, request.getParameter(name));
         }

         String tradeNo = params.get("out_trade_no");
         String gmtPayment = params.get("gmt_payment");
         String alipayTradeNo = params.get("trade_no");
         // 支付宝验签
         if (Factory.Payment.Common().verifyNotify(params)) {
            // 验签通过

            ObjectMapper objectMapper = new ObjectMapper();//json转换工具
            Jedis resource = jedisPool.getResource();//获取连接

            List<String> orderListStr = resource.lrange(tradeNo, 0, -1);//获取全部数据
            if (orderListStr != null) {
               List<Order> orderList = orderListStr.stream()
                       .map(s -> {
                          try {
                             return objectMapper.readValue(s, Order.class);
                          } catch (JsonProcessingException e) {
                             throw new RuntimeException(e);
                          }
                       })
                       .filter(order -> String.valueOf(order.getOrderId()).equals(tradeNo))// 过滤出当前订单
                       .collect(Collectors.toList());// 转换为List
                if (!orderList.isEmpty()) {

                   orderServiceImpl.saveBatch(orderList);// 批量保存订单

                   // 生成 productNumberParamList
                   List<ProductNumberParam> productNumberParamList = new ArrayList<>();
                   for (Order order : orderList) {
                      ProductNumberParam productNumberParam = new ProductNumberParam();
                      productNumberParam.setProductId(order.getProductId());
                      productNumberParam.setProductNum(order.getProductNum());
                      productNumberParamList.add(productNumberParam);
                   }
                   //发送消息给MQ 扣减库存
                   /**
                    *  交换机: topic.ex
                    *  routingkey: sub.number
                    *  消息: 商品id和减库存数据集合
                    */
                   rabbitTemplate.convertAndSend("topic.ex","sub.number",productNumberParamList);


                }
            }

            System.out.println("交易名称: " + params.get("subject"));
            System.out.println("交易状态: " + params.get("trade_status"));
            System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
            System.out.println("商户订单号: " + params.get("out_trade_no"));
            System.out.println("交易金额: " + params.get("total_amount"));
            System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
            System.out.println("买家付款时间: " + params.get("gmt_payment"));
            System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));

            // 更新订单已支付的逻辑代码

         }
      }
      return "success";
   }

}
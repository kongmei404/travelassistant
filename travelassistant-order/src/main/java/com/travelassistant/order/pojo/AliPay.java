package com.travelassistant.order.pojo;
 
import lombok.Data;
 
/**
 * @Author 
 * @Date Created in  2024/1/13 15:26
 * @DESCRIPTION: alipay接口参数
 * @Version V1.0
 */
@Data
public class AliPay {
   //订单编号
   private String traceNo;
   //商品金额
   private double totalAmount;
   //商品名称
   private String subject;
   //订单追踪号，商户自己生成，可已不使用
   private String alipayTraceNo;

   public AliPay(String traceNo, double totalAmount, String subject, String alipayTraceNo) {
      this.traceNo = traceNo;
      this.totalAmount = totalAmount;
      this.subject = subject;
      this.alipayTraceNo = alipayTraceNo;
   }
}
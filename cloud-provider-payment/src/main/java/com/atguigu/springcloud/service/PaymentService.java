package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {
    Payment getPaymentById(Long id);  //读取
    String payment_Timeout(Integer id);
    //测试基于异常的熔断
    String paymentCircuitBreaker(Integer id) ;
}

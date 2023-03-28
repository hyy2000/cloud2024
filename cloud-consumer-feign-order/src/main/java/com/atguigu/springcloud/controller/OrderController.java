package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.feign.PaymentFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {
//    @Autowired
//    RestTemplate restTemplate;
    @Autowired
    private PaymentFeignClient paymentFeignClient;

    @GetMapping("consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable Long id){
        String url = "http://"+  "CLOUD-PAYMENT-SERVICE"  +"/payment/get/" + id;
        return paymentFeignClient.getPaymentById(id);
//        return new CommonResult<>(200,"查询成功",commonResult.getData());
    }

    @GetMapping("/consumer/payment/feign/timeout")
    public String paymentFeignTimeout(){
        return paymentFeignClient.paymentFeignTimeout();
    }

}

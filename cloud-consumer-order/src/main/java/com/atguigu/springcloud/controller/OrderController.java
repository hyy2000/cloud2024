package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable Long id){
        //在order下，调用provider
        // http://localhost:8001/payment/31 (provider下的接口)  rest风格的接口

//        String url = "http://localhost:8001/payment/" + id;
        String url = "http://"+  "CLOUD-PAYMENT-SERVICE"  +"/payment/get/" + id;
        CommonResult<Payment> commonResult = restTemplate.getForObject(url, CommonResult.class);
        return commonResult;
//        return new CommonResult<>(200,"查询成功",commonResult.getData());
    }

}

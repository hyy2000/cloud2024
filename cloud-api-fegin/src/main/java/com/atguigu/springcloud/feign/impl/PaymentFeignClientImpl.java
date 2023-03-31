package com.atguigu.springcloud.feign.impl;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.feign.PaymentFeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

public class PaymentFeignClientImpl implements PaymentFeignClient {
    @Override
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id, @RequestHeader("X-Request-red") String header, @RequestParam("red") String rpm) {
        return new CommonResult<>(200,"getPaymentById的降级逻辑");
    }

    @Override
    public String paymentCircuitBreaker(Integer id) {
        return "基于异常的熔断的降级逻辑";
    }

    @Override
    public String paymentFeignTimeout() {
        return "paymentFeignTimeout的降级逻辑";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "paymentInfo_TimeOut的降级逻辑";
    }
}

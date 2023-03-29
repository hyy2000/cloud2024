package com.atguigu.springcloud.feign.impl;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.feign.PaymentFeignClient;

public class PaymentFeignClientImpl implements PaymentFeignClient {
    @Override
    public CommonResult<Payment> getPaymentById(Long id) {
        return new CommonResult<>(200,"getPaymentById的降级逻辑");
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

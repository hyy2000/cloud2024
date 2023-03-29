package com.atguigu.springcloud.feign;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.feign.impl.PaymentFeignClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//指定通过该feign接口调用哪一个服务
@FeignClient(value = "CLOUD-PAYMENT-SERVICE",fallback = PaymentFeignClientImpl.class)
public interface PaymentFeignClient {
    @GetMapping("/payment/get/{id}")
    CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);
    //在普通类上定义@PatnVariable注解时value值可以不用声明，但是在Feign接口下使用该注解，则需要声明value

    @GetMapping("/payment/feign/timeout")
    String paymentFeignTimeout();
    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id);
}

package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.feign.PaymentFeignClient;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
//@DefaultProperties(defaultFallback = "defaultFallBack")
public class OrderController {
    @Autowired
    private PaymentFeignClient paymentFeignClient;


    //测试基于异常的熔断
    @GetMapping("consumer/payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id")Integer id){
        return paymentFeignClient.paymentCircuitBreaker(id);
    }

    @GetMapping("consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id, @RequestHeader("X-Request-red") String header, @RequestParam("red") String rpm){
        String url = "http://"+  "CLOUD-PAYMENT-SERVICE"  +"/payment/get/" + id;
        return paymentFeignClient.getPaymentById(id,header,rpm);
//        return new CommonResult<>(200,"查询成功",commonResult.getData());
    }


    @GetMapping("/consumer/payment/feign/timeout")
    public String paymentFeignTimeout(){
        return paymentFeignClient.paymentFeignTimeout();
    }


    @HystrixCommand(
            //兜底方案
//            fallbackMethod = "paymentInfo_TimeOutFallBack",
            //name超时参数名，value超时参数值
            commandProperties = @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")
    )

    @GetMapping("consumer/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
        String result = paymentFeignClient.paymentInfo_TimeOut(id);
        log.info("*******result:" + result);
        return result;
    }

    public String paymentInfo_TimeOutFallBack(Integer id){
        //兜底方法的返回值类型，参数必须和目标方法一致
        return "我是消费者兜底方案";
    }
    public String defaultFallBack(){
        return "默认全局兜底方法";
    }


}

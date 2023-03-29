package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Value("${server.port}")
    String port;

    //测试基于异常的熔断
    @GetMapping("/payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        return paymentService.paymentCircuitBreaker(id);
    }

    @GetMapping("/payment/feign/timeout")
    public String paymentFeignTimeout() {
        try {
            TimeUnit.SECONDS.sleep(3);   // 让线程休眠3秒
        } catch (Exception e) {
            e.printStackTrace();
        }
        return port;
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        return new CommonResult<>(200, "查询成功" + port, payment);
    }

    @HystrixCommand(
            //兜底方案
            fallbackMethod = "paymentInfo_TimeOutFallBack",
            //name超时参数名，value超时参数值
            commandProperties = @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    )
    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
        String result = paymentService.payment_Timeout(id);
        log.info("*******result:" + result);
        return result;
    }
    public String paymentInfo_TimeOutFallBack(Integer id){
        //兜底方法的返回值类型，参数必须和目标方法一致
        return "我是提供者的兜底方案";
    }

}

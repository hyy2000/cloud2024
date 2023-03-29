package com.atguigu.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.atguigu.springcloud.dao.PaymentDao;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDao paymentDao;

    //测试基于异常的熔断
    @HystrixCommand(
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//开启断路器功能，默认值是true
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//10s内调用次数达到10个以上，打开断路，默认是20个
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//断路多久后开始尝试恢复，默认是5s
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "50")//出错百分比，当达到次阈值后，开始断路，默认是50%
            }
    )
    @Override
    public String paymentCircuitBreaker(Integer id) {
        if (id < 0) {
            throw new RuntimeException("*****id 不能负数");
        }
        String serialNumber = IdUtil.simpleUUID();//hutool.cn工具包

        return Thread.currentThread().getName() + "\t" + "调用成功,流水号：" + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(Integer id) {
        return "id 不能负数，请稍候再试,(┬＿┬)/~~     id: " + id;

    }

    @Override
    public String payment_Timeout(Integer id) {
        int timeNumber = 6;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + "   paymentInfo_TimeOut,id：  " + id + "\t" + "呜呜呜" + " 耗时(秒)" + timeNumber;

    }

    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}

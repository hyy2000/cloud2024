package com.atguigu.springcloud;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
public class PaymentMain {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain.class,args);
    }
    @Bean  //用于注册Servlet
    public ServletRegistrationBean getServlet() {
        //用于抓取当前服务的数据流（hystrix的数据流）
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
//        用于注册Servlet
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        //设置Servlet加载时机
        registrationBean.setLoadOnStartup(1);
        //映射路径
        registrationBean.addUrlMappings("/hystrix.stream");
        //Servlet名称
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }

}

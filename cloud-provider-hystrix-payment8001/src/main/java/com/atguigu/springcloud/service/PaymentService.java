package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;


import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    /**
     * 服务降级
     */
    //可以运行成功的
    public String paymentInfo_ok(Integer id) {
        return Thread.currentThread().getName() + "paymentInfo_ok, id:  " + id;
    }

    //运行超时的
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            //设置超时时间为3秒钟，过了就使用兜底方案来解决
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public String paymentInfo_TimeOut(Integer id) {
        int timeout = 3;
        //int age =10/0;
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Thread.currentThread().getName() + "paymentInfo_TimeOut, id:  " + id;
    }

    public String paymentInfo_TimeOutHandler(Integer id) {
        return Thread.currentThread().getName() + "系统繁忙，请稍后在试 , id:  " + id + "/(ㄒoㄒ)/~~";
    }


    /**
     * 服务熔断
     */

    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),//时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")})//失败率达到多少后跳闸
    public String paymentCircuitBreaker(Integer id) {
        if (id < 0) {
            throw new RuntimeException("不能为负数");
        }
        return "线程池:" + Thread.currentThread().getName() + " paymentCircuitBreaker,流水:" + IdUtil.simpleUUID() + "\t" +
                "O(∩_∩)O~";
    }

    public String paymentCircuitBreaker_fallback(Integer id) {
        return "id不能为负，稍后重试";
    }


}

package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {

    @Override
    public String PaymentInfo_ok(Integer id) {
        return "-----PaymentFallbackService---PaymentInfo_ok---/(ㄒoㄒ)/~~";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "------PaymentFallbackService---paymentInfo_TimeOut---/(ㄒoㄒ)/~~";
    }
}

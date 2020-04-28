package com.atguigu.springcloud.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component

public class MyLogGateWayFilter implements GlobalFilter {

    @Override
    //设置过滤器的顺序越小优先级越高
    @Order(value = 0)
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("********** MyLogGateWayFilter:"+new Date());
        //获取你的访问路径是否携带了name这个属性
        String name = exchange.getRequest().getQueryParams().getFirst("name");
        if (name==null){
            System.out.println("用户名为null");
            //如果没有携带name属性给出一个状态，这里设置为406无法解析头部信息
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            System.out.println(exchange.getResponse().getStatusCode()+"***************");
            return  exchange.getResponse().setComplete();
        }
        System.out.println(exchange.getResponse().getStatusCode()+"----------------");
        //如果携带了name属性则交给第二个过滤器进行过滤如果后面没有过滤器则放回结果
        return chain.filter(exchange);
    }

}

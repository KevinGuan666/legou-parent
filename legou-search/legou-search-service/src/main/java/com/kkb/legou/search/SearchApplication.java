package com.kkb.legou.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication //spring boot
@EnableDiscoveryClient //将微服务注册到注册中心
@EnableFeignClients //通过feign调用其他微服务
@EnableCircuitBreaker //开启熔断,微服务容错保护
public class SearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }
}

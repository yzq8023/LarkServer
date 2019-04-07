package com.github.hollykunge.servicediscuss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableCaching
@EnableEurekaClient
@EnableFeignClients
@MapperScan("com.github.hollykunge.servicediscuss")
public class ServiceDiscussApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceDiscussApplication.class, args);
    }

}

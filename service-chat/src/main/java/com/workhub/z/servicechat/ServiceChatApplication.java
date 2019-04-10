package com.workhub.z.servicechat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@EnableAutoConfiguration
@MapperScan("com.workhub.z.servicechat.mapper")
public class ServiceChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceChatApplication.class, args);
    }

}


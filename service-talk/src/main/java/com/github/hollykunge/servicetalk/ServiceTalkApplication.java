package com.github.hollykunge.servicetalk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableCaching
@MapperScan("com.github.hollykunge.servicetalk")
@EnableFeignClients
public class ServiceTalkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceTalkApplication.class, args);
    }

}


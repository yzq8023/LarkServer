package com.workhub.z.servicechat;

import com.workhub.z.servicechat.server.IworkWebsocketStarter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@EnableCaching
@MapperScan("com.workhub.z.servicechat.dao")
public class ServiceChatApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ServiceChatApplication.class, args);
//        初始化网络
        IworkWebsocketStarter iworkWebsocketStarter = new IworkWebsocketStarter();
        iworkWebsocketStarter.run();
    }

}


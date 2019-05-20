package com.github.hollykunge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


/**
 * 启动一个服务注册中心提供给其他应用进行对话
 *
 * @author 邵东旭
 * @create 2019/05/08 15:00
 */
@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
public class ConfigBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(ConfigBootstrap.class, args);
    }
}

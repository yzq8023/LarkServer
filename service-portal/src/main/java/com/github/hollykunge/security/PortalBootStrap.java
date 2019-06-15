package com.github.hollykunge.security;

import com.github.hollykunge.security.common.annotation.EnableTioWebsocket;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <p>author  zhhongyu </p><br>
 * <p>date 2019年5月8日</p>br>
 * <p>门户服务启动类</p>br>
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.github.hollykunge.security.mapper")
public class PortalBootStrap {

    public static void main(String[] args) {
        SpringApplication.run(PortalBootStrap.class, args);
    }
}

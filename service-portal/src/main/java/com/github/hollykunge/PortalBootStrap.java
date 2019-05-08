package com.github.hollykunge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <p>author  zhhongyu </p><br>
 * <p>date 2019年5月8日</p>br>
 * <p>门户服务启动类</p>br>
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients({"com.github.hollykunge.feign"})
public class PortalBootStrap {
    public static void main(String[] args) {
        SpringApplication.run(PortalBootStrap.class, args);
    }
}

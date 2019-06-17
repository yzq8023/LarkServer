package com.github.hollykunge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * FastdfsClient客户端
 * @author  zhhongyu
 */
@SpringBootApplication
@EnableDiscoveryClient
public class FastdfsClientBootStrap {

    public static void main(String[] args) {
        SpringApplication.run(FastdfsClientBootStrap.class, args);
    }

    /**
     * 文件解析器
     * @return
     */
//    @Bean
//    public CommonsMultipartResolver multipartResolver(){
//        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
//        commonsMultipartResolver.setMaxUploadSize(62914560);
//        commonsMultipartResolver.setDefaultEncoding("UTF-8");
//        return commonsMultipartResolver;
//    }

}

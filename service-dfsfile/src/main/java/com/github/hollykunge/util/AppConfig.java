package com.github.hollykunge.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class AppConfig {
    @Value("${fdfs.nginxHost}")
    private String resHost;

    @Value("${fdfs.nginxPort}")
    private String storagePort;
}
package com.github.hollykunge.servicewebservice.serviceImpl;

import com.github.hollykunge.servicewebservice.service.EryuanUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;



@Service
public class ScheduledTaskService {
    @Autowired
    EryuanUserService eryuanUserService;

    @Value("${spring.cronb}")
    private String cronb;


 //   @Scheduled(cron = "0/5 * * * * ?")
    public void refreshEryuanUser() {
        System.out.println("cronbbb"+cronb);
        System.out.println("5秒执行测试");
      //  eryuanUserService.saveEryuanUser();
    }
}

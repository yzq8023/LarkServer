package com.github.hollykunge.job;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 定时器配置
 * @Author zhuqz
 * @Date 2019-06-12
 */
@Configuration
public class QuartzConfig {
    //读去配置文件执行定时任务安排
    @Value("${deal_unused_file_job_cron}")
    private String deal_unused_file_job_cron;

    //----------------------------处理无用附件定时任务-------------------------------------------------------------
    @Bean
    public JobDetail unUsedFileQuartz() {
        return JobBuilder.newJob(UnUsedFileDealTask.class).withIdentity("unUsedFileQuartz").storeDurably().build();
    }
    @Bean
    public Trigger dealUnUsedFileTrigger() {
        return TriggerBuilder.newTrigger().forJob(unUsedFileQuartz())
                .withIdentity("unUsedFileQuartz")
                .withSchedule(CronScheduleBuilder.cronSchedule(deal_unused_file_job_cron))
                .build();
    }

}
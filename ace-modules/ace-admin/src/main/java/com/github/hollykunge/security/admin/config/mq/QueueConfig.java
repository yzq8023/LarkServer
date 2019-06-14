package com.github.hollykunge.security.admin.config.mq;

import com.github.hollykunge.security.common.constant.CommonConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 队列配置  可以配置多个队列
 * @author zhhongyu
 * @create 2019/5/9 13:25
 */
@Configuration
public class QueueConfig {

    @Bean
    public Queue noticeQueue() {
        /**
         durable="true" 持久化 rabbitmq重启的时候不需要创建新的队列
         auto-delete 表示消息队列没有在使用时将被自动删除 默认是false
         exclusive  表示该消息队列是否只在当前connection生效,默认是false
         */
        return new Queue(CommonConstants.NOTICE_QUEUE_NAMA,true,false,false);
    }
}
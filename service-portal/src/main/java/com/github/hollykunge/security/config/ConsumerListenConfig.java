package com.github.hollykunge.security.config;

import com.github.hollykunge.security.common.vo.rpcvo.DiscussVO;
import com.github.hollykunge.security.constants.Constants;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息消费者
 * @author zhhongyu
 * @create 2019/5/9 17:32
 */
@Component
@RabbitListener(queues = {Constants.PORTAL_QUEUE_NAMA},containerFactory = "rabbitListenerContainerFactory")
public class ConsumerListenConfig {
    @RabbitHandler
    public void handleMessage(DiscussVO message) throws Exception {
//        DiscussVO discussVO = (DiscussVO)message;
        // 处理消息
        System.out.println("Consumer handleMessage :"+message);
    }
}
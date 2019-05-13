package com.github.hollykunge.security.config;

import com.github.hollykunge.security.common.constant.CommonConstants;
import com.github.hollykunge.security.common.vo.rpcvo.DiscussVO;
import com.github.hollykunge.security.common.vo.rpcvo.TaskVO;
import com.github.hollykunge.security.common.vo.rpcvo.TodoVO;
import com.github.hollykunge.security.constants.Constants;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息消费者
 * @author zhhongyu
 * @create 2019/5/9 17:32
 */
@Component
@RabbitListener(queues = CommonConstants.PORTAL_QUEUE_NAMA,containerFactory = "rabbitListenerContainerFactory")
public class ConsumerListenConfig {
    @RabbitHandler
    public void handleMessage(DiscussVO message) throws Exception {
        // 处理消息
        System.out.println("ConsumerDiscussVO handleMessage :"+message);
//        int i = 1/0;
    }

    @RabbitHandler
    public void handleMessage(TaskVO message) throws Exception {
        // 处理消息
        System.out.println("ConsumerTaskVO handleMessage :"+message);
    }

    @RabbitHandler
    public void handleMessage(TodoVO message) throws Exception {
        // 处理消息
        System.out.println("ConsumerTodoVO handleMessage :"+message);
    }
}
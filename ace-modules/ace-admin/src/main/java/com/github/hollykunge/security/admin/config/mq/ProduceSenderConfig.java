package com.github.hollykunge.security.admin.config.mq;

import com.github.hollykunge.security.common.constant.CommonConstants;
import com.github.hollykunge.security.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;


/**
 * 消息发送  生产者1
 * @author zhhongyu
 * @create 2019/5/9 14:28
 */
@Slf4j
@Component
public class ProduceSenderConfig{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息,使用发送消息mq确认机制
     * @param message  消息
     */
    public void send(String id,Object message) {
        //消息id
        CorrelationData correlationId = new CorrelationData(id);
        rabbitTemplate.convertAndSend(CommonConstants.NOTICE_EXCHANGE, "",message, correlationId);
    }
}
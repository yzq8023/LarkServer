package com.github.hollykunge.security.config;

import com.github.hollykunge.security.common.constant.CommonConstants;
import com.github.hollykunge.security.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 消息发送  生产者1
 * @author zhhongyu
 * @create 2019/5/9 14:28
 */
@Slf4j
@Component
public class ProduceSenderConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     * @param uuid
     * @param message  消息
     */
    public void send(String uuid,Object message) {
        //解决消费消息幂等性时用到，门户消费不涉及幂等性重复数据的问题
        CorrelationData correlationId = new CorrelationData(uuid);
        rabbitTemplate.convertAndSend(CommonConstants.PORTAL_EXCHANGE, "",message, correlationId);
    }
}
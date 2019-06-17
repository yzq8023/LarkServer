package com.github.hollykunge.security.admin.config.mq;

import com.github.hollykunge.security.admin.constant.AdminCommonConstant;
import com.github.hollykunge.security.admin.entity.Notice;
import com.github.hollykunge.security.admin.mapper.NoticeMapper;
import com.github.hollykunge.security.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;

/**
 * RabbitMq配置
 * @author zhhongyu
 * @create 2019/5/9 13:37
 */
@Configuration
@Slf4j
public class RabbitMqConfig {
    @Resource
    private NoticeMapper noticeMapper;


    @Autowired
    private QueueConfig queueConfig;
    @Autowired
    private ExchangeConfig exchangeConfig;

    /**
     * 连接工厂
     */
    @Autowired
    private ConnectionFactory connectionFactory;

    /**
     将消息队列和交换机进行绑定
     */
    @Bean
    @Order(value = 5)
    public Binding binding_portal() {
        return BindingBuilder.bind(queueConfig.noticeQueue()).to(exchangeConfig.fanoutExchange());
    }
    //// 死信队列与死信交换机进行绑定
    @Bean
    @Order(value = 6)
    public Binding bindingDeadExchange() {
        return BindingBuilder.bind(queueConfig.noticDeadQueue()).to(exchangeConfig.noticDeadExchange()).with(AdminCommonConstant.DEAD_LETTER_ROUTING_KEY);
    }

    /**
     * 定义rabbit template用于数据的接收和发送
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (!ack) {
                    Notice notice = new Notice();
                    notice.setIsSend("0");
                    notice.setId(correlationData.getId());
                    noticeMapper.updateByPrimaryKeySelective(notice);
                    System.out.printf("message>>> "+correlationData.getId()+">>>未被发布到mq服务,已经补偿到了notice信息集...");
                }
            }
        });
        return template;
    }

}
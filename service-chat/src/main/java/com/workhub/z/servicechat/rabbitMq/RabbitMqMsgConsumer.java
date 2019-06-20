package com.workhub.z.servicechat.rabbitMq;

import com.github.hollykunge.security.common.vo.rpcvo.ContactVO;
import com.rabbitmq.client.Channel;
import com.workhub.z.servicechat.config.common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

//消息测试接收，上线注释掉
//@Component
public class RabbitMqMsgConsumer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //一个生产者，一个消费者
    @RabbitListener(queues = RabbitConfig.QUEUE_CONTACT,containerFactory="simpleRabbitListenerContainerFactory")
    public void process(List<ContactVO> userList, Message message, Channel channel) {
        logger.info("测试：接收处理队列A当中的消息");
        try {
            for(ContactVO vo:userList){
                logger.info("接收消息："+vo.getId());
            }
            // 消息删除
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            //模拟异常
            //int i=1/0;
        }catch (Exception e){
            logger.error(common.getExceptionMessage(e));//报错信息打印日志
            try {
                //发送异常消息回滚到队列，接着消费
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
            }catch (Exception ex){
                logger.error(common.getExceptionMessage(ex));//报错信息打印日志
            }

        }

    }
}

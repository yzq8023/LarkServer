package com.github.hollykunge.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMq配置
 * @author zhhongyu
 * @create 2019/5/9 13:37
 */
@Configuration
@Slf4j
public class RabbitMqConfig {


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
    public Binding binding_portal() {
        return BindingBuilder.bind(queueConfig.portalQueue()).to(exchangeConfig.fanoutExchange());
    }
    /**
     * queue listener  观察 监听模式
     * 当有消息到达时会通知监听在对应的队列上的监听对象
     * @return
     */
//    @Bean
//    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
//        //加载处理消息A的队列
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//        //设置接收多个队列里面的消息，这里设置接收队列portal
//        //假如想一个消费者处理多个队列里面的信息可以如下设置：
//        //container.setQueues(queueA(),queueB(),queueC());
//        container.setQueues(queueConfig.portalQueue());
//        container.setExposeListenerChannel(true);
//        //设置最大的并发的消费者数量
//        container.setMaxConcurrentConsumers(10);
//        //最小的并发消费者的数量
//        container.setConcurrentConsumers(1);
//        //设置确认模式手工确认
//        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        container.setMessageListener(new ChannelAwareMessageListener() {
//            @Override
//            public void onMessage(Message message, Channel channel) throws Exception {
//                /**通过basic.qos方法设置prefetch_count=1，这样RabbitMQ就会使得每个Consumer在同一个时间点最多处理一个Message，
//                 换句话说,在接收到该Consumer的ack前,它不会将新的Message分发给它 */
//                channel.basicQos(1);
//                byte[] body = message.getBody();
//                log.info("接收处理队列A当中的消息:" + new String(body));
//                /**为了保证永远不会丢失消息，RabbitMQ支持消息应答机制。
//                 当消费者接收到消息并完成任务后会往RabbitMQ服务器发送一条确认的命令，然后RabbitMQ才会将消息删除。*/
//                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//            }
//        });
//        return container;
//    }

    /**
     * 定义rabbit template用于数据的接收和发送
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        return template;
    }

}
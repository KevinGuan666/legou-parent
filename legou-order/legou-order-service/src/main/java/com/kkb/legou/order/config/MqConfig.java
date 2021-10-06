package com.kkb.legou.order.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
/**
 * 延迟30分钟取消订单
 * @Des 新职课商城项目
 * @Author 奕杭
 * @Date 2020/12/9 10:10
 */
@Configuration
public class MqConfig {


    @Autowired
    private Environment env;


    /**
     * 正常交换机
     * @return
     */
    @Bean
    public Exchange ttlExchange() {
        return ExchangeBuilder.directExchange(env.getProperty("mq.order.exchange.ttl")).durable(true).build();
    }


    /**
     * 死信交换机
     * @return
     */
    @Bean
    public Exchange dlxExchange() {
        return ExchangeBuilder.directExchange(env.getProperty("mq.order.exchange.dlx")).durable(true).build();
    }


    /**
     * 正常队列
     * @return
     */
    @Bean
    public Queue ttlQueue() {
        return QueueBuilder
                .durable(env.getProperty("mq.order.queue.ttl"))
                //死信交换机名称
                .withArgument("x-dead-letter-exchange", env.getProperty("mq.order.exchange.dlx"))
                //发送给死信交换机的routingkey
                .withArgument("x-dead-letter-routing-key", env.getProperty("mq.order.routing.dlx"))
                //队列过期时间，30秒过期
                .withArgument("x-message-ttl", 30000).build();
    }

    /**
     * 死信队列
     * @return
     */
    @Bean
    public Queue dlxQueue() {
        return QueueBuilder.durable(env.getProperty("mq.order.queue.dlx")).build();
    }

    /**
     * 正常交换机，队列绑定 * @param queue
     * @param exchange * @return
     */
    @Bean
    public Binding ttlBinding(@Qualifier("ttlQueue") Queue queue, @Qualifier("ttlExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(env.getProperty("mq.order.routing.ttl")).noargs();
    }

    /**
     * 死信交换机，队列绑定
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding dlxBinding(@Qualifier("dlxQueue") Queue queue, @Qualifier("dlxExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(env.getProperty("mq.order.routing.dlx")).noargs();
    }

}

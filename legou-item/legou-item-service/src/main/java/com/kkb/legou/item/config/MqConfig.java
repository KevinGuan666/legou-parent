package com.kkb.legou.item.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MqConfig {

    @Autowired
    Environment env;

    /**
     * 发送商品上架信息的交换机
     * @return
     */
    @Bean
    public Exchange updateExchange(){
        return ExchangeBuilder.directExchange(env.getProperty("mq.item.exchange.update")).durable(true).build();
    }

    /**
     * 声明queue
     * @return
     */
    @Bean
    public Queue updateQueue(){
        return QueueBuilder.durable(env.getProperty("mq.item.queue.update")).build();
    }

    /**
     * 绑定queue和exchange
     * @param exchange
     * @param queue
     * @return
     */
    @Bean
    public Binding updateBinding(@Qualifier("updateExchange") Exchange exchange, @Qualifier("updateQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(env.getProperty("mq.item.routing.key")).noargs();
    }
}

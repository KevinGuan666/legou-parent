package com.kkb.legou.pay.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class mqConfig {

    @Autowired
    private Environment env;

    /**
     * 发送订单以支付消息的交换机
     * @return
     */
    @Bean
    public Exchange orderExchange(){
        return ExchangeBuilder.directExchange(env.getProperty("mq.pay.exchange.order")).durable(true).build();
    }

    /**
     * 声明queue
     * @return
     */
    @Bean
    public Queue orderQueue() {
        return QueueBuilder.durable(env.getProperty("mq.pay.queue.order")).build();
    }


    /**
     * 绑定queue和exchange
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding orderBinding(@Qualifier("orderQueue") Queue queue, @Qualifier("orderExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(env.getProperty("mq.pay.routing.key")).noargs();
    }
}



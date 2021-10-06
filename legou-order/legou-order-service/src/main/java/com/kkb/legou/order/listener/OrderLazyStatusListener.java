package com.kkb.legou.order.listener;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.kkb.legou.item.po.Sku;
import com.kkb.legou.order.client.SkuClient;
import com.kkb.legou.order.po.Order;
import com.kkb.legou.order.po.OrderItem;
import com.kkb.legou.order.service.IOrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RabbitListener(queues = "${mq.order.queue.dlx}")
public class OrderLazyStatusListener {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private SkuClient skuClient;

    @RabbitHandler
    public void handlerData(String msg) {
        System.out.println(msg);
        if (StringUtils.isNotEmpty(msg)) {
            Long id = Long.parseLong(msg);
            Order order = orderService.getById(id);
            order.setOrderStatus("3");
            orderService.updateById(order);//修改订单装填
            //回滚库存(作业)
            List<OrderItem> list = orderService.getOrderItemByOrderId(id);
            for(OrderItem orderItem : list){
                int num = orderItem.getNum();
                skuClient.rollBackCount(num, orderItem.getSkuId());
            }

        }
    }

}



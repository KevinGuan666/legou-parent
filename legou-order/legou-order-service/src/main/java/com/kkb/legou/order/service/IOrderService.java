package com.kkb.legou.order.service;

import com.kkb.legou.core.service.ICrudService;
import com.kkb.legou.order.po.Order;
import com.kkb.legou.order.po.OrderItem;

import java.util.List;

public interface IOrderService extends ICrudService<Order> {


    /**
     * 增加订单
     * @param order */
    public void add(Order order);

    /**
     * 修改订单完成支付
     * @param outTradeNo
     * @param tradeNo
     */
    public void updatePayStatus(String outTradeNo, String tradeNo);

    public List<OrderItem> getOrderItemByOrderId(Long orderId);


}

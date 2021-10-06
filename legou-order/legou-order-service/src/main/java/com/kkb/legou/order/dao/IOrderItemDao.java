package com.kkb.legou.order.dao;

import com.kkb.legou.core.dao.ICrudDao;
import com.kkb.legou.order.po.OrderItem;

import java.util.List;

public interface IOrderItemDao extends ICrudDao<OrderItem> {

    public List<OrderItem> getOrderItem(Long orderId);

}

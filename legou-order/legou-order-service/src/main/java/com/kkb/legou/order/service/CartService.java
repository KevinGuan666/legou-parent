package com.kkb.legou.order.service;


import com.kkb.legou.order.po.OrderItem;

import java.util.List;

public interface CartService {

    /**
     * 添加购物车
     * @param id spuID
     * @param num 购买数量
     * @param username 用户名, 从登录令牌中获得
     */
    public void add(Long id, Integer num, String username);



    /**
     * 从redis中获取当前的用户的购物⻋的列表数据
     * @param username
     * @return
     */
    public List<OrderItem> list(String username);

}

package com.kkb.legou.seckill.service;

import com.kkb.legou.core.service.ICrudService;
import com.kkb.legou.seckill.po.SeckillOrder;

public interface ISeckillOrderService extends ICrudService<SeckillOrder> {

/***
 * 添加秒杀订单
 * @param id:商品ID
 * @param time:商品秒杀开始时间
 * @param username:用户登录名
 * @return
 */
    Boolean add(Long id, String time, String username);
}

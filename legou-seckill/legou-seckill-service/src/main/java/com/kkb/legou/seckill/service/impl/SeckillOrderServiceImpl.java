package com.kkb.legou.seckill.service.impl;

import com.kkb.legou.common.utils.IdWorker;
import com.kkb.legou.common.utils.SystemConstants;
import com.kkb.legou.core.service.impl.CrudServiceImpl;
import com.kkb.legou.seckill.dao.SeckillGoodsDao;
import com.kkb.legou.seckill.dao.SeckillOrderDao;
import com.kkb.legou.seckill.po.SeckillGoods;
import com.kkb.legou.seckill.po.SeckillOrder;
import com.kkb.legou.seckill.pojo.SeckillStatus;
import com.kkb.legou.seckill.service.ISeckillOrderService;
import com.kkb.legou.seckill.timer.MultiThreadingCreateOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SeckillOrderServiceImpl extends CrudServiceImpl<SeckillOrder> implements ISeckillOrderService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SeckillGoodsDao seckillGoodsDao;

    @Autowired
    private MultiThreadingCreateOrder multiThreadingCreateOrder;

    /****
     * 添加订单
     * @param id
     * @param time
     * @param username
     */
    @Override
    public Boolean add(Long id, String time, String username) {

        /* 在redis中存储用户是否抢单,结构:
           namespace UserQueueCount
             - username 次数
        */
        Long userQueueCount = redisTemplate.boundHashOps(SystemConstants.SEC_KILL_QUEUE_REPEAT_KEY).increment (username, 1);
        //判断 是否大于1 如果是,返回 ,否则 就放行 重复了.
        if(userQueueCount > 1){
            throw new RuntimeException("秒杀重复排队");
        }

        SeckillStatus seckillStatus = new SeckillStatus(username, new Date(), 1, id, time);
        // 将秒杀排队信息，left push存入redis的list队列中
        redisTemplate.boundListOps(SystemConstants.SEC_KILL_USER_QUEUE_KEY).leftPush(seckillStatus);

        // 多线程抢单
        multiThreadingCreateOrder.createOrder();

        return true;
    }
}

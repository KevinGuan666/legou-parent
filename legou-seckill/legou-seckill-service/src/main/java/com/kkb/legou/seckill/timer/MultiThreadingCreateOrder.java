package com.kkb.legou.seckill.timer;

import com.kkb.legou.common.utils.IdWorker;
import com.kkb.legou.common.utils.SystemConstants;
import com.kkb.legou.seckill.dao.SeckillGoodsDao;
import com.kkb.legou.seckill.po.SeckillGoods;
import com.kkb.legou.seckill.po.SeckillOrder;
import com.kkb.legou.seckill.pojo.SeckillStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MultiThreadingCreateOrder {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SeckillGoodsDao seckillGoodsDao;

    @Autowired
    private IdWorker idWorker;
    /***
     * 多线程下单操作
     */
    @Async
    public void createOrder(){

        //从队列中获取排队信息
        SeckillStatus seckillStatus = (SeckillStatus) redisTemplate.boundListOps(SystemConstants.SEC_KILL_USER_QUEUE_KEY).rightPop();

        try {
            if(seckillStatus != null){
                //时间区间
                String time = seckillStatus.getTime();
                //用户登录名
                String username=seckillStatus.getUsername();
                //用户抢购商品
                Long id = seckillStatus.getGoodsId();

                //获取商品数据
                SeckillGoods seckillGood = (SeckillGoods) redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + time).get(id);

                //如果没有库存，则直接抛出异常
                if(seckillGood == null || seckillGood.getStockCount() <= 0){
                    throw new RuntimeException("已售罄");
                }

                //如果有库存，则创建秒杀商品订单
                SeckillOrder seckillOrder = new SeckillOrder();
                seckillOrder.setId(idWorker.nextId());
                seckillOrder.setSeckillId(id);
                seckillOrder.setMoney(seckillGood.getCostPrice());
                seckillOrder.setUserId(username);
                seckillOrder.setCreateTime(new Date());
                seckillOrder.setStatus("0");

                //模拟下单耗时操作
                try {
                    System.out.println("*********************开始模拟下单操作 ***************************");
                    Thread.sleep(10000);
                    System.out.println("*********************结束模拟下单操作 ***************************");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //将秒杀订单存入到Redis中
                redisTemplate.boundHashOps(SystemConstants.SEC_KILL_ORDER_KEY).put(username, seckillOrder);

                //库存减少
                seckillGood.setStockCount(seckillGood.getStockCount() - 1);

                //判断当前商品是否还有库存
                if(seckillGood.getStockCount() <= 0){
                    //并且将商品数据同步到MySQL中
                    seckillGoodsDao.updateById(seckillGood);
                    redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + time).delete(id);
                }else{
                    redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + time).put(id, seckillGood);
                }
            }
        } catch (Exception e) {
                e.printStackTrace();
        }
    }

}

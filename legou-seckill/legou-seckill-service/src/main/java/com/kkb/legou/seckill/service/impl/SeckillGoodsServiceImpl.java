package com.kkb.legou.seckill.service.impl;

import com.kkb.legou.common.utils.SystemConstants;
import com.kkb.legou.core.service.impl.CrudServiceImpl;
import com.kkb.legou.seckill.po.SeckillGoods;
import com.kkb.legou.seckill.service.ISeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeckillGoodsServiceImpl extends CrudServiceImpl<SeckillGoods> implements ISeckillGoodsService {

    @Autowired
    private RedisTemplate redisTemplate;


    /***
     * Redis中根据Key获取秒杀商品列表
     * @param key
     * @return
     */
    @Override
    public List<SeckillGoods> list(String key) {
        List<SeckillGoods> list = redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + key).values();
        return list;
    }


    /****
     * 根据商品ID查询商品详情
     * @param time:时间区间
     * @param id:商品ID
     * @return
     */
    @Override
    public SeckillGoods getOne(String time, Long id) {
        SeckillGoods seckillGood = (SeckillGoods) redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + time).get(id);
        return seckillGood;
    }
}

package com.kkb.legou.seckill.service;

import com.kkb.legou.core.service.ICrudService;
import com.kkb.legou.seckill.po.SeckillGoods;

import java.util.List;

public interface ISeckillGoodsService extends ICrudService<SeckillGoods> {


    /***
     * 获取指定时间对应的秒杀商品列表
     * @param key
     */
    List<SeckillGoods> list(String key);


    /****
     * 根据ID查询商品详情
     * @param time:时间区间
     * @param id:商品ID
     */
    SeckillGoods getOne(String time, Long id);
}

package com.kkb.legou.seckill.timer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kkb.legou.common.utils.DateUtil;
import com.kkb.legou.common.utils.SystemConstants;
import com.kkb.legou.seckill.dao.SeckillGoodsDao;
import com.kkb.legou.seckill.po.SeckillGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SeckillGoodsPushTask {


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SeckillGoodsDao seckillGoodsDao;


    //反复被执行的方法 隔5秒钟执行一次
    @Scheduled(cron = "0/30 * * * * ?")
    public void loadGoodsPushRedis(){

        //1.获取当前的时间对应的5个时间段
        List<Date> dateMenus = DateUtil.getDateMenus();

        //2.循环遍历5个时间段 获取到时间的日期
        for(Date dateMenu : dateMenus){
            String extName = DateUtil.data2str(dateMenu, DateUtil.PATTERN_YYYYMMDDHH);
            //3.将循环到的时间段 作为条件 从数据库中执行查询 得出数据集
            /**
             * select * from seckill_goods_ where
             stock_count>0
             and `status`='1'
             and start_time > 开始时间段
             and end_time < 开始时间段+2hour and id not in (redis中已有的id)
             */
            QueryWrapper<SeckillGoods> query = Wrappers.<SeckillGoods>query();
            query.gt("stock_count_", 0);
            query.eq("status_", 1);
            query.eq("date_menu_", extName);

            //排除掉redis已有的商品
            Set keys = redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + extName).keys();
            if(keys != null && keys.size() > 0){
                query.notIn("id_", keys);
            }
            List<SeckillGoods> seckillGoods = seckillGoodsDao.selectList(query);
            //4.将数据集存储到redis中(key field value的数据格式 )
            /**
                * key(时间段:2019090516)
                *      field (id:1)  value(商品的数据pojo)
                *      field (id:2)  value(商品的数据pojo)
                *
                * key(时间段:2019090518)
                *      field (id:3)  value(商品的数据pojo)
                *      field (id:4)  value(商品的数据pojo)
             */

            for(SeckillGoods seckillGood : seckillGoods){
                redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + extName).put(seckillGood.getId(), seckillGood);
                //设置有效期
                redisTemplate.expireAt(SystemConstants.SEC_KILL_GOODS_PREFIX + extName, DateUtil.addDateHour(dateMenu, 2));
            }
        }
    }
}

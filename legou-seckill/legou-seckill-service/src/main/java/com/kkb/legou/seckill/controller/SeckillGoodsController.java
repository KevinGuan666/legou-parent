package com.kkb.legou.seckill.controller;

import com.kkb.legou.common.utils.DateUtil;
import com.kkb.legou.core.controller.BaseController;
import com.kkb.legou.seckill.po.SeckillGoods;
import com.kkb.legou.seckill.service.ISeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/seckill-goods")
@CrossOrigin
public class SeckillGoodsController extends BaseController<ISeckillGoodsService, SeckillGoods> {

    @Autowired
    private ISeckillGoodsService service;

    @GetMapping("/menus")
    public List<Date> datemenus(){
        List<Date> dates = DateUtil.getDateMenus();
        for (Date date : dates) {
            System.out.println(date);
        }
        return dates;
    }

    @RequestMapping("/list/{time}")
    public List<SeckillGoods> list(@PathVariable("time") String time){
        List<SeckillGoods> list = service.list(time);
        for (SeckillGoods seckillGoods : list) {
            System.out.println("id是:"+seckillGoods.getId());
        }
        return list;
    }

    @GetMapping("/one")
    public SeckillGoods one(String time, Long id){
        SeckillGoods seckillGood = service.getOne(time, id);
        return seckillGood;
    }
}

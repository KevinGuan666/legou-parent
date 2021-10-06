package com.kkb.legou.seckill.controller;

import com.kkb.legou.core.controller.BaseController;
import com.kkb.legou.seckill.po.SeckillOrder;
import com.kkb.legou.seckill.service.ISeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seckill-order")
@CrossOrigin
public class SeckillOrderController extends BaseController<ISeckillOrderService, SeckillOrder> {

    @Autowired
    private ISeckillOrderService service;

    @RequestMapping("/add")
    public ResponseEntity add(String username, String time, Long id){
        try{
            Boolean bo = service.add(id, time, username);
            if(bo){
                //抢单成功
                return ResponseEntity.ok("抢单成功");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity("服务器繁忙，请稍后再试", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

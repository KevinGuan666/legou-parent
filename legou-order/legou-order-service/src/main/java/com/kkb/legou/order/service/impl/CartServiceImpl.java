package com.kkb.legou.order.service.impl;

import com.kkb.legou.item.po.Sku;
import com.kkb.legou.item.po.Spu;
import com.kkb.legou.order.client.SkuClient;
import com.kkb.legou.order.client.SpuClient;

import com.kkb.legou.order.po.OrderItem;
import com.kkb.legou.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private SkuClient skuClient;

    @Autowired
    private SpuClient spuClient;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public void add(Long id, Integer num, String username) {

        if(num<=0){ //删除掉原来的商品
            redisTemplate.boundHashOps("Cart_" + username).delete(id);
            return;
        }

        Sku data = skuClient.edit(id);
        if(data != null){
            Long spuId = data.getSpuId();
            Spu spu = spuClient.edit(spuId);

            //3.将数据存储到 购物⻋对象(order_item)中
            OrderItem orderItem = new OrderItem();
            orderItem.setCategoryId1(spu.getCid1());
            orderItem.setCategoryId2(spu.getCid2());
            orderItem.setCategoryId3(spu.getCid3());
            orderItem.setSpuId(spu.getId());
            orderItem.setSkuId(id);
            orderItem.setName(data.getTitle());// 商品的名称 sku的名称
            orderItem.setPrice(data.getPrice());// sku的单价
            orderItem.setNum(num);// 购买的数量
            orderItem.setPayMoney(orderItem.getNum() * orderItem.getPrice());// 单价* 数量
            orderItem.setImage(data.getImages());//商品的图片地址

            // 4.数据添加到redis中 key:用户名 field:sku的ID value:购物⻋数据
            redisTemplate.boundHashOps("Cart_" + username).put(id, orderItem); // hset key field value hget key field
        }

    }


    @Override
    public List<OrderItem> list(String username) {
        List<OrderItem> orderItemList = redisTemplate.boundHashOps("Cart_" + username).values();
        return orderItemList;
    }
}

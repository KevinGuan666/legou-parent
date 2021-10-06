package com.kkb.legou.order.controller;

import com.kkb.legou.order.config.TokenDecode;
import com.kkb.legou.order.po.OrderItem;
import com.kkb.legou.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartController {


    @Autowired
    private CartService cartService;

    @Autowired
    private TokenDecode tokenDecode;


/**
 * 添加购物⻋ *
 * @param id 要购买的商品的SKU的ID
 * @param num 要购买的数量
 * @return
 */
    @RequestMapping("/add")
    public ResponseEntity add(Long id, Integer num) throws IOException {
        //springsecurity 获取当前的用户名 传递service String username = "lxs";
        Map<String, String> userInfo = tokenDecode.getUserInfo();
        String username = userInfo.get("user_name");
        //System.out.println("用户名:"+username);

        //String username = "gyh";
        cartService.add(id, num, username);
        return ResponseEntity.ok("添加成功");
    }

    @RequestMapping("/list")
    public ResponseEntity<List<OrderItem>> list() throws IOException {
        //String username = "gyh";
        Map<String, String> userInfo = tokenDecode.getUserInfo();
        String username = userInfo.get("user_name");
        //System.out.println("哇塞::用户名:"+username);
        List<OrderItem> orderItemList = cartService.list(username);
        return new ResponseEntity<>(orderItemList, HttpStatus.OK);

    }


}

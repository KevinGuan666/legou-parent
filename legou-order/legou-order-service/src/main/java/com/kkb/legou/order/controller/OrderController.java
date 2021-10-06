package com.kkb.legou.order.controller;

import com.kkb.legou.core.controller.BaseController;
import com.kkb.legou.order.config.TokenDecode;
import com.kkb.legou.order.po.Order;
import com.kkb.legou.order.po.OrderItem;
import com.kkb.legou.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseController<IOrderService, Order> {

    @Autowired
    private TokenDecode tokenDecode;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Order order) throws IOException {
        order.setUsername(tokenDecode.getUserInfo().get("user_name"));
        service.add(order);
        return ResponseEntity.ok("下单成功");
    }


}

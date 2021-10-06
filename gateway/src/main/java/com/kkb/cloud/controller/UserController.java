package com.kkb.cloud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kkb.cloud.client.UserClient;
import com.kkb.cloud.util.BCrypt;
import com.kkb.cloud.util.JwtUtil;
import com.kkb.legou.security.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserClient userClient;

    @Autowired
    private ObjectMapper om;

    @RequestMapping("/login")
    public ResponseEntity login(String username, String password) throws Exception {
        //1.从数据库中查询用户名对应的用户的对象
        User user = userClient.getByUserName(username);
        if (user == null) {
            //2.判断用户是否为空 为空返回数据
            return new ResponseEntity("用户名密码错误", HttpStatus.UNAUTHORIZED);
        }
            //3如果不为空 判断 密码是否正确 正确则登录成功
        if(BCrypt.checkpw(password, user.getPassword())){
            //成功
            Map<String,Object> info = new HashMap<String,Object>();
            info.put("role","USER");
            info.put("success","SUCCESS");
            info.put("username",username);

            //1.生成令牌
            String jwt = JwtUtil.createJWT(UUID.randomUUID().toString(), om.writeValueAsString(info), null);
            return ResponseEntity.ok(jwt);
        }else{
            //失败
            return new ResponseEntity("用户名密码错误", HttpStatus.UNAUTHORIZED);

        }
    }

}

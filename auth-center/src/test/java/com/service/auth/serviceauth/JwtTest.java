package com.service.auth.serviceauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;


public class JwtTest {

    @Test
    public void testCreateJwt() throws Exception{
        //存储秘钥的工厂对象
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("mickey.jks"), "mickey".toCharArray());

        //密钥对(公钥-》私钥)
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("mickey", "mickey".toCharArray());

        //私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        //自定义payload信息
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("id", 123);
        tokenMap.put("name", "mickey");
        tokenMap.put("roles", "r01, r02, admin");

        //使用工具类，通过私钥颁发JWT令牌
        Jwt jwt = JwtHelper.encode(new ObjectMapper().writeValueAsString(tokenMap), new RsaSigner(privateKey));
        String token = jwt.getEncoded();
        System.out.println(token);
    }

/**
 * 使用公钥校验令牌
 */
    @Test
    public void testVerify() {
        //令牌
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6InIwMSwgcjAyLCBhZG1pbiIsIm5hbWUiOiJtaWNrZXkiLCJpZCI6MTIzfQ.dv2GECe4H8Vrfb3df9eK7BrJyhLIf9VPcXpZTo9K5aeASOYzUXI2rU-X5fsPDG8DRCtyZDLhlngN0HYrib5QJiLZACmvYEapMjY5R6_XW3Hq8jJPZJW6yyKfdjTBkoNuGO9Hkd22iXtylqOYQGyO3pTku0-yovEfxx1W-HA5jbyFwEN_plAIO4fathBWxgQGVzmBQohsYl8AGuIWwZLO4diADzfa3mVXhVlN5bYVLPOFLM7TICdeqqBEAhyNdDMjzGGnmI3UYAtNQVeCpuhDrk2Rxw8H_nGrlp13Is2vgbvQx6fDYtmMzRa-yDY9l92f6laNWGELK_UsVPxbPLQx3w";

        //公钥
        String publicKey = "";

        //校验令牌
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publicKey));
        String claims = jwt.getClaims();
        System.out.println(claims);
    }
}

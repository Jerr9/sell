package com.o2ii.sell.util;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class WechatAccessTokenUtil {
    @Resource
    private RedisUtil redisUtil;

    public void getAccessToken() {
//        String token = redisUtil.get("token");
        redisUtil.set("token", "token", 1000);


    }
}

package com.o2ii.sell.controller;

import com.o2ii.sell.constant.CookieConstant;
import com.o2ii.sell.constant.RedisConstant;
import com.o2ii.sell.enums.BasicEnum;
import com.o2ii.sell.result.ResponseData;
import com.o2ii.sell.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("seller")
@Slf4j
public class SellerInfoController {

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String sellerLogin(HttpServletResponse response) {
        String uuid = UUID.randomUUID().toString();

        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, uuid), "openidxxxxxxx22M", RedisConstant.TOKEN_EXPIRE, TimeUnit.SECONDS);
        System.out.println(System.currentTimeMillis());
        CookieUtil.set(response, CookieConstant.TOKEN, uuid, CookieConstant.EXPIRE);
        return "登录成功";
    }

    @RequestMapping(value = "testCookie")
    public String testCookie(HttpServletRequest request) {
        String token = CookieUtil.get(request, "token").getValue();
        log.info(token);
        System.out.println(redisTemplate.opsForValue().get(RedisConstant.TOKEN_PREFIX + token));
        System.out.println(redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, token)).equals("openidxxxxxxx22M"));
        return "返回成功" + CookieUtil.get(request, "token").getValue();
    }

    @RequestMapping(value = "logout")
    public ResponseData logout(HttpServletRequest request, HttpServletResponse response) {
        String token = CookieUtil.get(request, "token").getValue();

        redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, token));
        CookieUtil.set(response ,CookieConstant.TOKEN, null, 1);

        return new ResponseData(BasicEnum.SUCCESS);
    }

}

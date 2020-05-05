package com.o2ii.sell.aspect;

import com.o2ii.sell.constant.CookieConstant;
import com.o2ii.sell.constant.RedisConstant;
import com.o2ii.sell.enums.BusinessEnum;
import com.o2ii.sell.exception.GlobalException;
import com.o2ii.sell.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 对 com.o2ii.sell.controller.Seller*.*(..) 进行身份校验， 排除登录登出的部分 com.o2ii.sell.controller.SellerInfoController.*(..).
     */
    @Pointcut("execution(* com.o2ii.sell.controller.Seller*.*(..))" + "&& (!execution(* com.o2ii.sell.controller.SellerInfoController.*(..)))")
    public void verify() {}

    @Before("verify()")
    public void doVerify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 查询Cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        String userOpenid = (String)redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));

        log.info("cookie = {}, userOpenid = {}", cookie.getValue(), userOpenid);
        if (userOpenid == null || "".equals(userOpenid)) {
            log.warn("【登陆校验】Cookie中查不到token");
            throw new GlobalException(BusinessEnum.AUTHORIZE_ERROR);
        }
    }
}

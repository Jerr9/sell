package com.o2ii.sell.runner;

import com.o2ii.sell.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WechatTokenRunner implements ApplicationRunner {
    // 实现 ApplicationRunner、CommandLineRunner 接口的类，可以在SpringBoot启动后自动执行run方法

    @Autowired
    private WxMpService wxMpService;

//    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("WeChatTokenRunner");
//        try {
//            Object cacheToken = redisUtil.get("WECHAT_ACCESS_TOKEN");
//            Object cacheToken1 = redisUtil.get("WECHAT_ACCESS_TOKEN1");
//            if (cacheToken1 == null) {
//                System.out.println("if");
//            }
//            else {
//                System.out.println("else");
//            }
//            if (cacheToken == null) {
//                String token = wxMpService.getAccessToken();
//                System.out.println("==== new token ====");
//                System.out.println(token);
//                System.out.println("==== new token ====");
//                redisUtil.set("WECHAT_ACCESS_TOKEN", token, 7140);
//            }
//        }
//        catch(WxErrorException e) {
//            log.error("【WeChatTokenRunner执行失败】 = {}", e.getError().getErrorMsg());
//        }

    }
}

package com.o2ii.sell.controller;

import com.o2ii.sell.enums.ResultEnum;
import com.o2ii.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/wechat")
@Slf4j
public class WeChatAuthorizeController {

    @Autowired
    private WxMpService wxMpService;

    @GetMapping(value = "/authorize")
    public String authorize() {
        // 1配置
        // 2调用方法
        String url = "http://rda7qu.natappfree.cc/sell/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_USER_INFO, "http://rda7qu.natappfree.cc/sell/index.html/");

        log.info("【微信网页授权】{}", redirectUrl);
        // TODO【redirect:】重定向关键字，配合【@Controller】使用，不能跟【@RestController】配合使用
        return "redirect:" + redirectUrl;
    }

    @GetMapping(value = "/userInfo")
    public String userInfo(@RequestParam("code") String code, @RequestParam("state") String returnUrl) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("【通过code获取微信信息成功】= {}", wxMpOAuth2AccessToken.toString());
        return "redirect:" + returnUrl + "?openId=" + openId;

    }
}

package com.o2ii.sell.controller;

import com.o2ii.sell.config.BaseConfig;
import com.o2ii.sell.enums.BusinessEnum;
import com.o2ii.sell.exception.GlobalException;
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
    private BaseConfig baseConfig;
    // 我的测试号微信对应openId为：oPD5Ew98b3ijk89i5Orz38ThHy14

    @Autowired
    private WxMpService wxMpService;

    @GetMapping(value = "/authorize")
    public String authorize() {
        // 1配置
        // 2调用方法
        String url = baseConfig.getBaseDomain() + "/sell/wechat/userInfo",
                state = baseConfig.getBaseDomain() + "/sell/index.html/";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_USER_INFO, state);

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
            throw new GlobalException(BusinessEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("【通过code获取微信信息成功】= {}", wxMpOAuth2AccessToken.toString());
        return "redirect:" + returnUrl + "?openId=" + openId;

    }

    @GetMapping(value = "openAuthorize")
    public String openAuthorize(@RequestParam(name = "code", required = true) String code) {
        System.out.println(code);
        return "redirect:" + "http://www.baidu.com";
    }
}

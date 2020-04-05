package com.o2ii.sell.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/wx")
public class WxController {

    private String APPID = "wx8e61a539e50ac0ef";
    private String SECRET = "9a77386f2b9cdfcda5d573b9849b5bc4";

    /**
     * 用于校验开发者服务器，根据微信wiki要求校验signature是否正确，正确返回echostr即可
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @GetMapping(value = "/auth", produces = "text/plain;charset=utf-8")
    public String authGet(@RequestParam(name = "signature", required = false) String signature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestParam(name = "echostr", required = false) String echostr) {
        System.out.println(signature + "," + timestamp + "," + nonce + "," + echostr);
        return echostr;
    }

    /**
     * 通过code获取网页授权access_token和openid
     * @param code
     * @return
     */
    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public String wxAuthorizedCallback(@RequestParam("code") String code) {
        System.out.println("获取网页授权code：" + code);
        String api = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APPID + "&secret=" + SECRET + "&code=" + code + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(api, String.class);
        System.out.println(result);
        return code;
    }
}

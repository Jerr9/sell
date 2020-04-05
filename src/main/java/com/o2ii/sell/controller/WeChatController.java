package com.o2ii.sell.controller;

import com.o2ii.sell.enums.ResultEnum;
import com.o2ii.sell.enums.VOEnum;
import com.o2ii.sell.exception.SellException;
import com.o2ii.sell.util.RedisUtil;
import com.o2ii.sell.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "wechat")
public class WeChatController {
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping(value = "jsapiSignature")
    public ResultVO jsapiSignature(@RequestBody Map<String, String> map) {
        String clientUrl = map.get("clientUrl");
        WxJsapiSignature wxJsapiSignature;
        try {
            wxJsapiSignature = wxMpService.createJsapiSignature(clientUrl);
        }
        catch(WxErrorException e) {
            log.error("【jsapiSignature签名失败】= {}", e.getError().getErrorMsg());
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("appid", wxJsapiSignature.getAppId());
        resultMap.put("noncestr", wxJsapiSignature.getNonceStr());
        resultMap.put("signature", wxJsapiSignature.getSignature());
        resultMap.put("timestamp", String.valueOf(wxJsapiSignature.getTimestamp()));
        log.info("【jsapiSignature签名结果】= {}", resultMap.toString());
        ResultVO<Map> resultVO = new ResultVO(VOEnum.SUCCESS, resultMap);
        return resultVO;
    }

    @PostMapping(value = "uploadCallback")
    public ResultVO uploadCallback(@RequestBody Map<String, String> map) {
        String accessToken = "32_WgDmj56bMEmNsUptd02Ff4fO4lokzAqGcejVWuyTDlZa8TkgPiZ2Nut1m5XGxOhog7F4g8ppGQLr6EhOkGPLd2alPqx4D9WlFuZ42tlDQM-pw5zDkeV6i26y6FuwjL72CFvkZbYOhCS8nwB5PUKjABALQG";
        String wxApi = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=" + accessToken + "&media_id=" + map.get("mediaId");
        System.out.println(map.get("mediaId"));
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(wxApi, String.class);
        log.info("【获取临时素材成功】 = {}", result);
        return new ResultVO(VOEnum.SUCCESS, result);
    }

    @PostMapping(value = "redisTest")
    public ResultVO redisTest() {
        redisUtil.set("token", "tokenValue", 10000);
        return new ResultVO(VOEnum.SUCCESS);
    }

}

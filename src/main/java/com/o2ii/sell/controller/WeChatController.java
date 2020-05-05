package com.o2ii.sell.controller;

import com.o2ii.sell.enums.BasicEnum;
import com.o2ii.sell.enums.BusinessEnum;
import com.o2ii.sell.exception.GlobalException;
import com.o2ii.sell.result.ResponseData;
import com.o2ii.sell.util.FetchTmpUtil;
import com.o2ii.sell.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "wechat")
@ConfigurationProperties(prefix = "paths.file")
public class WeChatController {
    private String image;

    public void setImage(String path) {
        this.image = path;
    }

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping(value = "jsapiSignature")
    public ResponseData jsapiSignature(@RequestBody Map<String, String> map) {
        String clientUrl = map.get("clientUrl");
        WxJsapiSignature wxJsapiSignature;
        try {
            wxJsapiSignature = wxMpService.createJsapiSignature(clientUrl);
        }
        catch(WxErrorException e) {
            log.error("【jsapiSignature签名失败】= {}", e.getError().getErrorMsg());
            throw new GlobalException(BusinessEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("appid", wxJsapiSignature.getAppId());
        resultMap.put("noncestr", wxJsapiSignature.getNonceStr());
        resultMap.put("signature", wxJsapiSignature.getSignature());
        resultMap.put("timestamp", String.valueOf(wxJsapiSignature.getTimestamp()));
        log.info("【jsapiSignature签名结果】= {}", resultMap.toString());
        ResponseData<Map> resultVO = new ResponseData(BasicEnum.SUCCESS, resultMap);
        return resultVO;
    }

    @PostMapping(value = "uploadCallback")
    public ResponseData uploadCallback(@RequestBody Map<String, String> map) {
        String accessToken = null;
        try {
            accessToken = wxMpService.getAccessToken();
        } catch(Exception e) {
            e.printStackTrace();
        }
        String wxApi = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=" + accessToken + "&media_id=" + map.get("mediaId");
        System.out.println(map.get("mediaId"));
        System.out.println(wxApi);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(wxApi, String.class);

        FetchTmpUtil.fetchTmpFile(wxApi, image);

        log.info("【获取临时素材成功】 = {}", result);
        return new ResponseData(BasicEnum.SUCCESS);
    }

    @PostMapping(value = "redisTest")
    public ResponseData redisTest() {
//        wxMpService.getAccessToken();
//        redisUtil.set("token", "tokenValue", 10000);
        log.info("【测试日志打印】");
        System.out.println(this.image + "xxxxxx");
        return new ResponseData(BasicEnum.SUCCESS);
    }


}

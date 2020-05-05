package com.o2ii.sell.service.impl;

import com.o2ii.sell.dto.OrderDTO;
import com.o2ii.sell.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {
    @Autowired
    private WxMpService wxMpService;

    @Override
    public void orderStatus(OrderDTO orderDTO) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId("KmosYySO-maY14KGrwhlun4DqrrQdkQiDIw2U-TYNP4");
        templateMessage.setToUser("oPD5Ew98b3ijk89i5Orz38ThHy14");

        List<WxMpTemplateData> templateList= Arrays.asList(
                new WxMpTemplateData("first", "亲，请记得收货"),
                new WxMpTemplateData("keyword1", "微信下单")
//                new WxMpTemplateData("keyword2", orderDTO.getOrderId()),
//                new WxMpTemplateData("keyword3", orderDTO.getBuyerName())
        );
        templateMessage.setData(templateList);
        try {

            String res = wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
            log.error("【微信模板消息】发送失败 = {}", res);
            log.error("【wxMpService.getAccessToken();】= {}", wxMpService.getAccessToken());
            log.error("【wxMpService.getAccessToken();】= {}", wxMpService.getAccessToken());
            log.error("【wxMpService.getAccessToken();】= {}", wxMpService.getAccessToken());
        }
        catch(WxErrorException e) {
            log.error("【微信模板消息】发送失败 = {}", e);
        }
    }
}

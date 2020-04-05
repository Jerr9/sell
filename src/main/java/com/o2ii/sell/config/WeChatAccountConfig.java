package com.o2ii.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("wechat")
public class WeChatAccountConfig {
    private String appId;
    private String appSecret;
}

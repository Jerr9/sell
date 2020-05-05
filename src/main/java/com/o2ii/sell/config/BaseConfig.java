package com.o2ii.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("base-config.domains")
@Data
public class BaseConfig {
    private String baseDomain;
}

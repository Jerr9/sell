package com.o2ii.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("monitor")
@Slf4j
public class MonitorController {

    @GetMapping("record")
    public void record(
            @RequestParam String openId,
            @RequestParam String mobile,
            @RequestParam String uid,
            @RequestParam String code,
            @RequestParam String api,
            @RequestParam String timestamp
                         ) {
            log.info("【异常监控：录入】=> " +
                        "openId:{}, " +
                        "mobile:{}, " +
                        "uid:{}, " +
                        "code:{}, " +
                        "api:{}, " +
                        "timestamp:{}",
                        openId,
                        mobile,
                        uid,
                        code,
                        api,
                        timestamp
                    );
    }
}

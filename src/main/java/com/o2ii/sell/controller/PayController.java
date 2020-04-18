package com.o2ii.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/pay")
public class PayController {

    @GetMapping("/create")
    public ModelAndView create() {
        log.info("【测试freemarker模板引擎】");
        Map<String, String> map = new HashMap<>();
        map.put("name", "SpringBoot");
        return new ModelAndView("views/create1", map);
    }
}

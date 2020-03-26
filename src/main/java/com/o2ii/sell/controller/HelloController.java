package com.o2ii.sell.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping(value = "/o2ii")
    public String hello() {

        System.out.print("ss");
        System.out.print("ss");
        System.out.print("ss");
        System.out.print("ss");
        System.out.print("ss");


        return "Hello o2ii";
    }
}

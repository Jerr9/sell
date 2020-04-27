package com.o2ii.sell.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    @Autowired
    HttpServletRequest request;

    public String getBaseParam() {
        return request.getParameter("BASE_PARAM");
    }

    public String getBaseHead() {
        return request.getHeader("BASE_HEAD");
    }
}

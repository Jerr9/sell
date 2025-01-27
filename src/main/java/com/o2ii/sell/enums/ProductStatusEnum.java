package com.o2ii.sell.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum {
    UP(1, "在架"),
    DOWN(0, "下架")
    ;

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

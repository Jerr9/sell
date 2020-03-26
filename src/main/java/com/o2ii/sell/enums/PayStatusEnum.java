package com.o2ii.sell.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnum {
    AWAIT(0, "待支付"),
    PAYED(1, "已支付"),
    ;

    private Integer code;

    private String msg;

    PayStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

package com.o2ii.sell.enums;

import lombok.Getter;

@Getter
public enum BasicEnum {
    SUCCESS(0, "操作成功"),
    FAIL(-9000, "操作失败"),
    UNKNOWN(-9500, "Oops"),
    ;

    private Integer code;

    private String msg;

    BasicEnum() {

    }

    BasicEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

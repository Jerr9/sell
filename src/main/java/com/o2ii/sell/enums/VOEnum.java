package com.o2ii.sell.enums;

import lombok.Getter;

@Getter
public enum VOEnum {
    SUCCESS(0, "操作成功"),
    FAIL(9999, "操作失败"),
    ;

    private Integer code;

    private String msg;

    VOEnum() {
    }

    VOEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

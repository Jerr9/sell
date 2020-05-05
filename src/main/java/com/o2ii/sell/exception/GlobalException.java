package com.o2ii.sell.exception;

import com.o2ii.sell.enums.BusinessEnum;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {

    private Integer code;

//    private String msg;

    public GlobalException(BusinessEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
//        this.msg = resultEnum.getMsg();
    }

    public GlobalException(Integer code, String msg) {
        super(msg);
        this.code = code;
//        this.msg = msg;
    }
}

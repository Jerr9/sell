package com.o2ii.sell.result;

import com.o2ii.sell.enums.BasicEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseData<T> implements Serializable {

    private static final long serialVersionUID = 2029608653070294747L;
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 描述信息
     */
    private String msg;

    /**
     * 内容
     */
    private T data;

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseData() {

    }

    public ResponseData(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseData(BasicEnum basicEnum) {
        this.code = basicEnum.getCode();
        this.msg = basicEnum.getMsg();
    }

    public ResponseData(BasicEnum basicEnum, T data) {
        this.code = basicEnum.getCode();
        this.msg = basicEnum.getMsg();
        this.data = data;
    }
}

package com.o2ii.sell.vo;

import com.o2ii.sell.enums.VOEnum;
import lombok.Data;

@Data
public class ResultVO<T> {
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

    public ResultVO() {

    }

    public ResultVO(VOEnum voEnum) {
        this.code = voEnum.getCode();
        this.msg = voEnum.getMsg();
    }

    public ResultVO(VOEnum voEnum, T data) {
        this.code = voEnum.getCode();
        this.msg = voEnum.getMsg();
        this.data = data;
    }
}

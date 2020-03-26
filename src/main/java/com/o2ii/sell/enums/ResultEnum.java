package com.o2ii.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_OUT_OF_STOCK(11, "商品库存不足"),
    ORDER_NOT_EXIST(12, "订单不存在"),
    ORDERDETAIL_NOT_EXIST(13, "订单详情不存在"),
    ORDER_STATUS_ERROR(14, "订单状态不正确"),
    ORDER_UPDATE_ERROR(15, "订单状态更新错误"),
//    ORDER_UPDATE_ERROR(16, "订单状态更新错误"),
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

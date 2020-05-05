package com.o2ii.sell.enums;

import lombok.Getter;

@Getter
public enum BusinessEnum {
    PARAM_ERROR(1, "参数不正确"),
    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_OUT_OF_STOCK(11, "商品库存不足"),
    ORDER_NOT_EXIST(12, "订单不存在"),
    ORDER_DETAIL_NOT_EXIST(13, "订单详情不存在"),
    ORDER_STATUS_ERROR(14, "订单状态不正确"),
    ORDER_UPDATE_ERROR(15, "订单状态更新错误"),
    ORDER_FINISH_ERROR(16, "结单错误"),
    CART_EMPTY(16, "购物车为空"),
    WECHAT_MP_ERROR(20, "微信调用异常"),
    PRODUCT_STATUS_ERROR(21, "商品状态错误"),
    PRODUCT_CATEGORY_NOT_EXIST(22, "商品品类错误"),
    PRODUCT_CATEGORY_ADD_ERROR(23, "添加商品品类错误"),
    PRODUCT_CATEGORY_EDIT_ERROR(24, "编辑商品品类错误"),



    AUTHORIZE_ERROR(4001, "当前用户未登录"),
    ;

    private Integer code;

    private String msg;

    BusinessEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

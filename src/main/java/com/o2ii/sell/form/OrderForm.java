package com.o2ii.sell.form;

import com.o2ii.sell.dataobject.OrderDetail;
import com.o2ii.sell.vo.OrderDetailVO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class OrderForm {
    @NotEmpty(message = "姓名必填")
    private String userName;

    @NotEmpty(message = "手机号必填")
    private String phone;

    @NotEmpty(message = "地址必填")
    private String address;

    @NotEmpty(message = "openId必填")
    private String openId;

//    private List<OrderDetail> items; // createOrder usage:1
    @NotEmpty(message = "购物车不能为空") // createOrder usage:2
    private String items; // createOrder usage:2
}

package com.o2ii.sell.vo;

import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

@Data
public class OrderDetailVO {
    private String detailId;

    private String orderId;

    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productQuantity;

    private String productIcon;
}

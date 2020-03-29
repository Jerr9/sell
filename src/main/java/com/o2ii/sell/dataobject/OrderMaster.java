package com.o2ii.sell.dataobject;

import com.o2ii.sell.enums.OrderStatusEnum;
import com.o2ii.sell.enums.PayStatusEnum;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "order_master")
public class OrderMaster {

    @Id
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "buyer_name")
    private String buyerName;

    @Column(name = "buyer_phone")
    private String buyerPhone;

    @Column(name = "buyer_address")
    private String buyerAddress;

    @Column(name = "buyer_openid")
    private String buyerOpenid;

    /**
     * 订单总金额
     */
    @Column(name = "order_amount")
    private BigDecimal orderAmount;

    @Column(name = "order_status")
    private Integer orderStatus = OrderStatusEnum.NEW.getCode(); // 新下单，默认0未支付

    @Column(name = "pay_status")
    private Integer payStatus = PayStatusEnum.AWAIT.getCode(); // 新下单，默认0未支付

    @Column(name = "create_time")
    private Date createTime;

    public OrderMaster() {

    }

    @Override
    public String toString() {
        return "OrderMaster{" +
                "orderId='" + orderId + '\'' +
                ", buyerName='" + buyerName + '\'' +
                ", buyerPhone='" + buyerPhone + '\'' +
                ", buyerAddress='" + buyerAddress + '\'' +
                ", buyerOpenid='" + buyerOpenid + '\'' +
                ", orderAmount='" + orderAmount + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", payStatus='" + payStatus + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}

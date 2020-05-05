package com.o2ii.sell.dataobject;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "seller_info")
public class SellerInfo {
    @Id
    @Column(name = "seller_id")
    private String sellerId;

    @Column(name = "openid")
    private String openId;

    @Column(name = "seller_name")
    private String sellerName;

    @Column(name = "password")
    private String password;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;
}


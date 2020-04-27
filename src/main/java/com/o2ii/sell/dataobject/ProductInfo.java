package com.o2ii.sell.dataobject;

import com.o2ii.sell.enums.ProductStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
//@DynamicUpdate
@Data
@Table(name = "product_info")
public class ProductInfo {

    @Id
    @Column(name = "product_id")
    private String productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private BigDecimal productPrice;

    @Column(name = "product_stock")
    private Integer productStock;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "product_icon")
    private String productIcon;

    @Column(name = "product_status")
    private Integer productStatus = ProductStatusEnum.DOWN.getCode();

    @Column(name = "category_type")
    private Integer categoryType;

    public ProductInfo() {
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productStock=" + productStock +
                ", productDescription='" + productDescription + '\'' +
                ", productIcon='" + productIcon + '\'' +
                ", productStatus=" + productStatus +
                ", categoryType=" + categoryType +
                '}';
    }
}

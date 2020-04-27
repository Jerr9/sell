package com.o2ii.sell.form;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ProductForm {

    private String productId;

    @NotEmpty(message = "产品名不能为空")
    private String productName;

    @NotNull(message = "产品价格不能为空")
    private BigDecimal productPrice;

    @NotNull(message = "产品库存不能为空")
    private Integer productStock;

    @NotEmpty(message = "产品描述不能为空")
    private String productDescription;

    private String productIcon;

    @NotNull(message = "产品品类不能为空")
    private Integer categoryType;
}

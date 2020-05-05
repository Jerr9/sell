package com.o2ii.sell.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductCategoryDTO {
    private Integer categoryType;
    private String categoryName;
    private Date createTime;
    private Date updateTime;
}
